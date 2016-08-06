package com.gems;

import com.gems.adapter.Adapter;
import com.gems.adapter.AdapterFactory;
import com.gems.ui.ProgressIndicator;
import com.gems.ui.formatter.BasicFormatter;

import java.io.IOException;
import java.net.URL;

/**
 * Created by nayana on 8/6/16.
 */
public class DownloadManager
{
    private DownloadList downloadList;

    private String downloadFolder;

    public DownloadManager(DownloadList downloadList, String downloadFolder)
    {
        this.downloadList = downloadList;
        this.downloadFolder = downloadFolder;
    }

    public void download()
    {

        ProgressIndicator progressIndicator = new ProgressIndicator(downloadList);
        progressIndicator.setFormatter(new BasicFormatter());

        System.out.println("download folder - " + downloadFolder);

        //iterate through collection (single thread mode first)
        for (DownloadableFile downloadbleFile : downloadList) {
            Adapter adapter = AdapterFactory.getAdapter(downloadbleFile.url.getProtocol());
            adapter.setOnProgressListener(progressIndicator);
            try {
                adapter.download(downloadbleFile, downloadFolder);
            } catch (IOException e) {

            }
        }
    }

    public void setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
    }
}
