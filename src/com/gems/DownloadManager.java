package com.gems;

import com.gems.ui.ProgressIndicator;
import com.gems.ui.formatter.BasicFormatter;
import com.gems.model.DownloadList;
import com.gems.model.DownloadableFile;
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

    public DownloadManager(DownloadList downloadList, ConfigFile configFile, ProgressIndicator progressIndicator) {
        this.downloadList = downloadList;
        this.configFile = configFile;
        this.progressIndicator = progressIndicator;
    }

    public void startDownload()
    {
        ExecutorService executor = Executors.newFixedThreadPool(configFile.getThreadCount());

        //iterate through collection (single thread mode first)
        Iterator iterator = getDownloadList().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            DownloadableFile downloadableFile = (DownloadableFile) pair.getValue();

            Downloader downloader = new Downloader(downloadableFile, progressIndicator, configFile);
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
