package com.gems.model;

import com.gems.ui.ProgressIndicator;

/**
 * Created by nayan on 8/7/16.
 */
public class Job {

    private DownloadList downloadList;

    private ProgressIndicator progressIndicator;

    public Job(DownloadList downloadList, ProgressIndicator progressIndicator)
    {
        this.downloadList = downloadList;
        this.progressIndicator = progressIndicator;
    }

    public DownloadList getDownloadList() {
        return downloadList;
    }

    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }
}
