package com.gems.ui.formatter;

import com.gems.model.Progress;

import java.net.URL;
/**
 * Created by nayan on 8/6/16.
 */
public interface Formatter {
    String format(URL url, Progress progress);
}
