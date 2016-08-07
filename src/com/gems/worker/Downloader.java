package com.gems.worker;

import com.gems.exception.AdapterNotFoundException;
import com.gems.model.Task;
import com.gems.adapter.Adapter;
import com.gems.adapter.AdapterFactory;
import com.gems.ui.ProgressIndicator;
import com.gems.util.ConfigFile;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.Runnable;
/**
 * Created by nayana on 8/6/16.
 */
public class Downloader implements Runnable
{
    protected Logger log = Logger.getLogger(Downloader.class.getName());

    protected Task task;

    protected ProgressIndicator progressIndicator;

    protected ConfigFile configFile;

    public Downloader(Task task, ProgressIndicator progressIndicator, ConfigFile configFile)
    {
        this.progressIndicator = progressIndicator;
        this.configFile = configFile;
        this.task = task;
    }

    public void run() {
        try {
            Adapter adapter = AdapterFactory.getAdapter(task.getUrl());
            adapter.setOnProgressListener(progressIndicator);
            adapter.download(task.getProgress(), configFile);
        } catch (IOException|AdapterNotFoundException e) {
            log.warn(task.getUrl() + " - Download error");
            task.getProgress().status = "error";
        }
    }
}
