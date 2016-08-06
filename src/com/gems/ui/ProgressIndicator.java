package com.gems.ui;

import com.gems.DownloadList;
import com.gems.DownloadableFile;
import java.net.URL;
import com.gems.Progress;
import com.gems.event.ProgressListener;
import com.gems.ui.formatter.Formatter;
import java.util.Iterator;
import java.util.Map;

import com.github.tomaslanger.chalk.Ansi;

/**
 * Created by nayana on 8/5/16.
 */
public class ProgressIndicator implements ProgressListener {

    private DownloadList downloadList;

    private Formatter formatter;

    public ProgressIndicator(DownloadList downloadList)
    {
        this.downloadList = downloadList;
    }

    public void setFormatter(Formatter formatter)
    {
        this.formatter = formatter;
    }

    public void onProgressEvent()
    {
        this.draw();
    }

    //display all files in progress
    //clear display and show again
    public void draw()
    {
        Iterator iterator = downloadList.iterator();
        System.out.print(Ansi.cursorUp(downloadList.size())); //move cursor up
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            Progress progress = ((DownloadableFile) pair.getValue()).progress;
            URL url = ((DownloadableFile) pair.getValue()).url;
            System.out.print(Ansi.eraseLine()); //erase current line
            System.out.print(formatter.format(url, progress));
        }
    }
}
