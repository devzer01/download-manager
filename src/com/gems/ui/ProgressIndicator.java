package com.gems.ui;

import com.gems.DownloadManager;
import com.gems.model.DownloadList;
import com.gems.model.Task;
import java.net.URL;
import com.gems.model.Progress;
import com.gems.event.ProgressListener;
import com.gems.ui.formatter.Formatter;
import java.util.Iterator;
import java.util.Map;

import com.github.tomaslanger.chalk.Ansi;

/**
 * Created by nayana on 8/5/16.
 *
 * abstract class for ProgressIndicator,
 */
abstract public class ProgressIndicator implements ProgressListener {

    protected DownloadManager downloadManager;

    protected Formatter formatter;

    protected boolean drawOnce = false;

    public ProgressIndicator(Formatter formatter)
    {
        this.formatter = formatter;
    }

    public void setDownloadManager(DownloadManager downloadManager)
    {
        this.downloadManager = downloadManager;
    }

    public void onProgressEvent()
    {
        this.draw();
    }

    //display all files in progress
    //clear display and show again
    abstract public void draw();
}
