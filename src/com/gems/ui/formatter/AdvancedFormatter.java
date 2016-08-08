package com.gems.ui.formatter;

import com.gems.model.Progress;

import java.net.URL;
/**
 * Created by nayana on 8/6/16.
 *
 * can be used to develop an advanced progress indicator with progress percentage display etc
 *
 * @not-in-use
 */
public class AdvancedFormatter implements Formatter
{
    public String format(URL url, Progress progress)
    {
        return progress.status.toString();
    }
}
