package com.gems.ui.formatter;

import com.gems.util.Progress;

import java.net.URL;
/**
 * Created by nayana on 8/6/16.
 */
public class AdvancedFormatter implements Formatter
{
    public String format(URL url, Progress progress)
    {
        return progress.status;
    }
}
