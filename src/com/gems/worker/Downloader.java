package com.gems.worker;

import com.gems.model.DownloadableFile;
import com.gems.adapter.Adapter;
import com.gems.adapter.AdapterFactory;
import com.gems.ui.ProgressIndicator;
import com.gems.util.ConfigFile;

import java.io.IOException;
import java.lang.Runnable;
/**
 * Created by nayana on 8/6/16.
 */
public class Downloader implements Runnable
{
    protected DownloadableFile downloadableFile;

    protected ProgressIndicator progressIndicator;

    protected ConfigFile configFile;

    public Downloader(DownloadableFile downloadableFile, ProgressIndicator progressIndicator, ConfigFile configFile)
    {
        this.progressIndicator = progressIndicator;
        this.configFile = configFile;
        this.downloadableFile = downloadableFile;
    }

    public void run() {
        Adapter adapter = AdapterFactory.getAdapter(downloadableFile.getUrl());
        adapter.setOnProgressListener(progressIndicator);
        try {
            adapter.download(downloadableFile.getProgress(), configFile.getDownloadFolder());
        } catch (IOException e) {

        }
    }
}
