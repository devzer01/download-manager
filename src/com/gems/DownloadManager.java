package com.gems;

import com.gems.ui.ProgressIndicator;
import com.gems.ui.formatter.BasicFormatter;
import com.gems.util.DownloadList;
import com.gems.util.DownloadableFile;
import com.gems.worker.Downloader;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

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
        ExecutorService executor = Executors.newFixedThreadPool(5);

        //iterate through collection (single thread mode first)
        Iterator iterator = downloadList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            DownloadableFile downloadbleFile = (DownloadableFile) pair.getValue();
            Downloader downloader = new Downloader(downloadbleFile);
            downloader.setProgressIndicator(progressIndicator);
            downloader.setDownloadFolder(downloadFolder);
            executor.execute(downloader);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

    public void setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
    }
}
