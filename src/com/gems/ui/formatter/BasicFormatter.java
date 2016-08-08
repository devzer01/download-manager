package com.gems.ui.formatter;

import com.gems.model.Progress;

import java.net.URL;

/**
 * Created by nayana on 8/6/16.
 *
 * A basic progress formatter based on original requirements 
 */
public class BasicFormatter implements Formatter
{
    public String format(URL url, Progress progress)
    {
        return url.getFile() + " - " + progress.status + " - " + progress.currentSize + "/" + String.valueOf(progress.size).toString() + "\n";
    }
}
