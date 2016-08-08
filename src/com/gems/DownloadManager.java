package com.gems;

import com.gems.ui.ProgressIndicator;
import com.gems.model.Job;
import com.gems.model.DownloadList;
import com.gems.model.Task;
import com.gems.util.ConfigFile;
import com.gems.worker.Downloader;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nayana on 8/6/16.
 */
public class DownloadManager {

    protected Logger log = Logger.getLogger(DownloadManager.class.getName());

    private DownloadList downloadList;

    private ConfigFile configFile;

    private ProgressIndicator progressIndicator;

    protected Job job = null;

    public DownloadManager(Job job, ConfigFile configFile) {
        this.job = job;
        this.configFile = configFile;
        this.progressIndicator = job.getProgressIndicator();
        this.progressIndicator.setDownloadManager(this);
    }

    public void startDownload()
    {
        ExecutorService executor = Executors.newFixedThreadPool(configFile.getThreadCount());

        //iterate through collection (single thread mode first)
        Iterator iterator = job.getDownloadList().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Task task = (Task) pair.getValue();
            task.setProgressIndicator(progressIndicator);
            Downloader downloader = new Downloader(task, progressIndicator, configFile);
            executor.execute(downloader);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            log.debug("Waiting for threads to finish download");
        }

    }

    public DownloadList getDownloadList() {
        return downloadList;
    }
}
