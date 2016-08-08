package com.gems.ui;

import com.gems.model.DownloadList;
import com.gems.model.Progress;
import com.gems.model.Task;
import com.gems.ui.formatter.Formatter;
import com.github.tomaslanger.chalk.Ansi;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by nayana on 8/8/16.
 *
 * ConsoleProgressIndicator (rename->) AnsiConsoleProgressIndicator
 * uses a 3rd party ANSI library that is capable of doing colors
 */
public class ConsoleProgressIndicator extends ProgressIndicator
{
    public ConsoleProgressIndicator(Formatter formatter)
    {
        super(formatter);
    }
    //display all files in progress
    //clear display and show again
    public void draw()
    {
        DownloadList downloadList = this.downloadManager.getDownloadList();
        Iterator iterator = downloadList.entrySet().iterator();
        if (this.drawOnce) System.out.print(Ansi.cursorUp(downloadList.entrySet().size())); //move cursor up
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            Task task = (Task) pair.getValue();
            Progress progress = task.getProgress();
            URL url = task.getUrl();
            System.out.print(Ansi.eraseLine()); //erase current line
            //TODO if progress.status == Status.ERROR then color red
            //TODO if progress.status == Status.DONE then green
            System.out.print(formatter.format(url, progress));
        }
        drawOnce = true;
    }
}
