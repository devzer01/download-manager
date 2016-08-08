/**
 * Created by nayana on 8/6/16.
 */

package com.gems.worker;

import com.gems.exception.AdapterNotFoundException;
import com.gems.model.Status;
import com.gems.model.Task;
import com.gems.adapter.Adapter;
import com.gems.adapter.AdapterFactory;
import com.gems.ui.ProgressIndicator;
import com.gems.util.ConfigFile;
import com.gems.util.Filename;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.Runnable;
import java.io.File;

/**
 * responsible for downloading a Task
 */
public class Downloader implements Runnable
{
    protected Logger log = Logger.getLogger(Downloader.class.getName());

    protected Task task;

    protected ConfigFile configFile;

    public Downloader(Task task, ConfigFile configFile)
    {
        this.configFile = configFile;
        this.task = task;
    }

    /**
     * design to run as a thread
     * gets the adapter based on task type
     * initiates the download
     * based on task progress status, saves the file to final location
     * or delete the temp file
     */
    public void run() {
        Adapter adapter = null;
        File file = null;
        try {
            adapter = AdapterFactory.getAdapter(task);
            file = adapter.download();
            if (task.getProgress().status == Status.DONE && task.getProgress().size == file.length()) {
                file.renameTo(new File(configFile.getDownloadFolder() + File.separator + Filename.UrlToFilename(task.getUrl())));
            } else {
                file.delete();
            }
        } catch (IOException|AdapterNotFoundException e) {
            log.warn(task.getUrl() + " - Download error");
            task.getProgress().status = Status.ERROR;
        }
    }
}
