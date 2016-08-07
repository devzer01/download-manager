package com.gems;

import com.gems.ui.ProgressIndicator;
import com.gems.model.Job;
import com.gems.model.DownloadList;
import com.gems.model.Task;
import com.gems.util.ConfigFile;
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

    private ConfigFile configFile;

    private ProgressIndicator progressIndicator;

    public DownloadManager(Job job, ConfigFile configFile) {
        this.downloadList = job.getDownloadList();
        this.configFile = configFile;
        this.progressIndicator = job.getProgressIndicator();
        this.progressIndicator.setDownloadManager(this);
    }

    public void startDownload()
    {
        ExecutorService executor = Executors.newFixedThreadPool(configFile.getThreadCount());

        //iterate through collection (single thread mode first)
        Iterator iterator = downloadList.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Task task = (Task) pair.getValue();

            Downloader downloader = new Downloader(task, progressIndicator, configFile);
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
}
