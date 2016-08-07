package com.gems;

import com.gems.ui.ProgressIndicator;
import com.gems.ui.formatter.BasicFormatter;
import com.gems.util.DownloadList;
import com.gems.util.DownloadableFile;
import com.gems.util.Progress;
import com.gems.worker.Downloader;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nayana on 8/6/16.
 */
public class DownloadManager {
    private DownloadList downloadList;

    private String downloadFolder;

    private ProgressIndicator progressIndicator;

    public DownloadManager() {

    }

    public DownloadManager(DownloadList downloadList, String downloadFolder) {
        this.setDownloadList(downloadList);
        this.setDownloadFolder(downloadFolder);
    }

    public void download()
    {
        progressIndicator.setFormatter(new BasicFormatter());
        ExecutorService executor = Executors.newFixedThreadPool(5);

        //iterate through collection (single thread mode first)
        Iterator iterator = getDownloadList().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            DownloadableFile downloadableFile = (DownloadableFile) pair.getValue();
            Downloader downloader = new Downloader(downloadableFile);
            downloader.setProgressIndicator(progressIndicator);
            downloader.setDownloadFolder(getDownloadFolder());
            executor.execute(downloader);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

    public DownloadList getDownloadList() {
        return downloadList;
    }

    public void setDownloadList(DownloadList downloadList) {
        this.downloadList = downloadList;
    }

    public String getDownloadFolder() {
        return downloadFolder;
    }

    public void setDownloadFolder(String downloadFolder) {

        this.downloadFolder = downloadFolder;
    }

    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }

    public void setProgressIndicator(ProgressIndicator progressIndicator) {
        progressIndicator.setDownloadList(downloadList);
        this.progressIndicator = progressIndicator;
    }
}
