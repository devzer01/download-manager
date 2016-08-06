package com.gems;

import java.net.URL;

/**
 * Created by nayana on 8/6/16.
 */
public class DownloadableFile
{
    public URL url;

    public Progress progress;

    public DownloadableFile(URL url, Progress progress)
    {
        this.url = url;
        this.progress = progress;
    }
}
