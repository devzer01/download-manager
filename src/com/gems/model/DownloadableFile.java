package com.gems.model;

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
        this.url = url;
        this.progress = progress;
    }

    public URL getUrl() {
        return url;
    }

    public Progress getProgress() {
        return progress;
    }

}
