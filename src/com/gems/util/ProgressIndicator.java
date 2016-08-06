package com.gems.util;

import com.gems.ProgressList;
import com.gems.Progress;
import com.github.tomaslanger.chalk.Ansi;
import java.util.Iterator;
import java.util.Map;
/**
 * Created by nayana on 8/5/16.
 */
public class ProgressIndicator {

    //display all files in progress
    //clear display and show again
    public static void drawProgress(ProgressList progressList)
    {
        Iterator iterator = progressList.entrySet().iterator();
        System.out.print(Ansi.cursorUp(progressList.size())); //move cursor up
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            Progress progress = (Progress) pair.getValue();
            System.out.print(Ansi.eraseLine()); //erase current line
            System.out.print(progress.name + " ----- " + progress.status + "\n");
        }
    }
}
