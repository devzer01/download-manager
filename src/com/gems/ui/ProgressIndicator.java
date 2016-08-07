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
 */
public class ProgressIndicator implements ProgressListener {

    private DownloadManager downloadManager;

    private Formatter formatter;

    private boolean drawOnce = false;

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
    public void draw()
    {
        DownloadList downloadList = downloadManager.getDownloadList();
        Iterator iterator = downloadList.entrySet().iterator();
        //if (drawOnce) System.out.print(Ansi.cursorUp(downloadList.entrySet().size())); //move cursor up
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            Task task = (Task) pair.getValue();
            Progress progress = task.getProgress();
            URL url = task.getUrl();
            System.out.print(Ansi.eraseLine()); //erase current line
            System.out.print(formatter.format(url, progress));
        }
        drawOnce = true;
    }
}
