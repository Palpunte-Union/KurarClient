package si.f5.pa_union.kurar.installer;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    private static boolean IS_FIRST_INSTALL = false;

    public static void main(String[] args) {
        downloadFileFromUrl("https://palpunte-union.github.io/KurarClient/launcher/latest/Launcher.jar", new File(getKurarDirectory(), "KurarClient.jar"));
        downloadFileFromUrl("https://palpunte-union.github.io/KurarClient/launcher/latest/Launcher.json", new File(getKurarDirectory(), "KurarClient.json"));
        JOptionPane.showMessageDialog(null, IS_FIRST_INSTALL ? "Thank you for installing KurarClient.\nIt was successfully installed." : "KurarClient was successfully installed.");
    }

    public static void downloadFileFromUrl(String url, File file) {
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            if (file.getParentFile().mkdirs()) {
                IS_FIRST_INSTALL = true;
            }
        }
        try {
            if (file.delete()) {
                throw new IOException();
            }

            HttpURLConnection con = (HttpURLConnection)(new URL(url)).openConnection();
            try(InputStream is = con.getInputStream(); FileOutputStream fos = new FileOutputStream(file)) {
                byte[] buff = new byte[8192];
                int read;
                while ((read = is.read(buff)) > -1) {
                    fos.write(buff, 0, read);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPlatformName() {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Mac") || osName.startsWith("Darwin"))
            return "osx";
        if (osName.toLowerCase().contains("windows"))
            return "windows";
        return "linux";
    }

    public static File getWorkingDirectory() {
        String applicationData, folder, userHome = System.getProperty("user.home", ".");
        switch (getPlatformName()) {
            case "linux":
                return new File(userHome, ".minecraft/");
            case "windows":
                applicationData = System.getenv("APPDATA");
                folder = (applicationData != null) ? applicationData : userHome;
                return new File(folder, ".minecraft/");
            case "osx":
                return new File(userHome, "Library/Application Support/minecraft");
        }
        return new File(userHome, "minecraft/");
    }

    public static File getVersionsDirectory() {
        return new File(getWorkingDirectory(), "versions");
    }

    public static File getKurarDirectory() {
        return new File(getVersionsDirectory(), "KurarClient");
    }
}
