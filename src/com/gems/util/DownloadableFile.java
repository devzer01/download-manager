package com.gems.util;

import java.net.URL;

/**
 * Created by nayana on 8/6/16.
 */
public class DownloadableFile
{
    protected URL url;

    protected Progress progress;

    public DownloadableFile(URL url, Progress progress)
    {
        this.setUrl(url);
        this.setProgress(progress);
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
}
