package si.f5.pa_union.kurar.launcher.resources.type;

import si.f5.pa_union.kurar.launcher.Utils;

import java.io.File;

public class Library {
    private String path;

    private String sha1;

    private int size;

    private String url;

    public Library(String path, String sha1, int size, String url) {
        this.path = path;
        this.sha1 = sha1;
        this.size = size;
        this.url = url;
    }

    public String getPath() {
        return this.path;
    }

    public File getFilePath() {
        return new File(Utils.getWorkingDirectory(), "libraries" + File.separator + this.path.replaceAll("//", File.separator));
    }

    public String getSha1() {
        return this.sha1;
    }

    public int getSize() {
        return this.size;
    }

    public String getUrl() {
        return this.url;
    }

    public String toString() {
        return this.path + " : " + this.sha1 + " : " + this.size;
    }
}
