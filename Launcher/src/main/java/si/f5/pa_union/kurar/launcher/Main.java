package si.f5.pa_union.kurar.launcher;

import si.f5.pa_union.kurar.launcher.resources.Manager;
import si.f5.pa_union.kurar.launcher.resources.type.Library;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static String LAUNCHER_VERSION = "1.0.0";
    public boolean isUpdated = false;

    public static void main(String[] args) {
        if (Utils.checkLauncherUpdate()) {
            JOptionPane.showMessageDialog(null, "ランチャーのアップデートがあります。");
            return;
        }
        new Main(args);
    }

    public Main(String[] args) {
        if(!Utils.getKurarDirectory().exists()) {
            if(Utils.getKurarDirectory().mkdir()) {
                try {
                    new Utils().downloadJson();
                    new Utils().downloadVersionJson();
                    new Downloader("https://palpunte-union.github.io/KurarClient/client/" + Utils.readURL() + "/KurarClient.jar").DownloadTo(Utils.getKurarDirectory().getPath());
                    isUpdated = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new NullPointerException();
            }
        } else {
            if(Utils.checkUpdate()) {
                try {
                    new Utils().downloadJson();
                    new Utils().downloadVersionJson();
                    if(new File(Utils.getKurarDirectory(), "KurarClient.jar").delete())
                        new Downloader("https://palpunte-union.github.io/KurarClient/client/" + Utils.readURL() + "/KurarClient.jar").DownloadTo(Utils.getKurarDirectory().getPath());
                    if(!new File(Utils.getKurarDirectory(), "KurarClient.jar").exists())
                        new Downloader("https://palpunte-union.github.io/KurarClient/client/" + Utils.readURL() + "/KurarClient.jar").DownloadTo(Utils.getKurarDirectory().getPath());
                    isUpdated = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        launch(args);
    }

    public void launch(String[] args) {
        final String javaLibraryPath = System.getProperty("java.home");

        Thread thread = new Thread(() -> {
            String temp;
            Manager manager = new Manager(new File(Utils.getKurarDirectory(), "KurarClient.json").getAbsolutePath());
            try {
                manager.download();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ("windows".equals(Utils.getPlatformName())) {
                temp = javaLibraryPath + "\\bin\\java.exe";
            } else {
                temp = javaLibraryPath + "/bin/java";
            }
            String cpseparator = Utils.getPlatformName().equals("windows") ? ";" : ":";
            StringBuilder classPathBuilder = new StringBuilder();
            classPathBuilder.append(new File(Utils.getKurarDirectory(), "KurarClient.jar").getAbsolutePath());
            for (Library lib : manager.getLibraries()) {
                classPathBuilder.append(cpseparator);
                classPathBuilder.append(lib.getFilePath().getAbsolutePath());
            }
            String[] launchArgs = args;
            List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            ArrayList<String> jvmArgs = new ArrayList<>();
            jvmArgs.add(temp);
            if (Utils.getPlatformName().equals("osx"))
                jvmArgs.add("-XstartOnFirstThread");
            if (launchArgs == null || launchArgs.length == 0) {
                String assets = (new File(Utils.getWorkingDirectory(), "assets")).getAbsolutePath();
                launchArgs = new String[]{"--version", "mcp", "--accessToken", "0", "--assetsDir", assets, "--assetIndex", Utils.getAssetsVersion(), "--userProperties", "{}"};
            } else {
                jvmArgs.addAll(inputArguments);
            }
            jvmArgs.add("-cp");
            jvmArgs.add(classPathBuilder.toString());
            jvmArgs.add("net.minecraft.client.main.Main");
            jvmArgs.addAll(Arrays.asList(launchArgs));
            jvmArgs.addAll(Arrays.asList("--updated", isUpdated ? "true" : "false"));
            StringBuilder sb = new StringBuilder("Launching game");
            for (String arg : jvmArgs)
                sb.append(" ").append(arg);
            System.out.println(sb);
            ProcessBuilder processBuilder = new ProcessBuilder(jvmArgs);
            processBuilder.redirectErrorStream(true);
            try {
                Process process = processBuilder.start();
                InputStreamReader isr = new InputStreamReader(process.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(0);
        }, "KurarClient");
        thread.start();
    }
}
