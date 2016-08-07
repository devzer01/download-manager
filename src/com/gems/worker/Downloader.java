package com.gems.worker;

import com.gems.util.DownloadableFile;
import com.gems.adapter.Adapter;
import com.gems.adapter.AdapterFactory;
import com.gems.ui.ProgressIndicator;

import java.io.IOException;
import java.lang.Runnable;
/**
 * Created by nayana on 8/6/16.
 */
public class Downloader implements Runnable
{
    protected DownloadableFile downloadableFile;

    protected ProgressIndicator progressIndicator;

    protected String downloadFolder;

    public Downloader(DownloadableFile downloadableFile)
    {
        this.downloadableFile = downloadableFile;
    }

    public void setProgressIndicator(ProgressIndicator progressIndicator)
    {
        this.progressIndicator = progressIndicator;
    }

    public void setDownloadFolder(String downloadFolder)
    {
        this.downloadFolder = downloadFolder;
    }

    public void run() {
        Adapter adapter = AdapterFactory.getAdapter(downloadableFile.getUrl().getProtocol());
        adapter.setOnProgressListener(progressIndicator);
        try {
            adapter.download(downloadableFile, downloadFolder);
        } catch (IOException e) {

        }
    }
}
