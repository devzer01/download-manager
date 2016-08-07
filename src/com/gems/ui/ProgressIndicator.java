package com.gems.ui;

import com.gems.util.DownloadList;
import com.gems.util.DownloadableFile;
import java.net.URL;
import com.gems.util.Progress;
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

    private boolean drawOnce = false;

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
        Iterator iterator = downloadList.entrySet().iterator();
        if (drawOnce) System.out.print(Ansi.cursorUp(downloadList.entrySet().size())); //move cursor up
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            DownloadableFile downloadableFile = (DownloadableFile) pair.getValue();
            Progress progress = downloadableFile.getProgress();
            URL url = downloadableFile.getUrl();
            System.out.print(Ansi.eraseLine()); //erase current line
            System.out.print(formatter.format(url, progress));
        }
        drawOnce = true;
    }
}
