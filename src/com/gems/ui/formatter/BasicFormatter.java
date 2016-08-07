package com.gems.ui.formatter;

import com.gems.model.Progress;

import java.net.URL;

/**
 * Created by nayana on 8/6/16.
 */
public class BasicFormatter implements Formatter
{
    public String format(URL url, Progress progress)
    {
        return url.getFile() + " ----- " + progress.status + "\n";
    }
}
