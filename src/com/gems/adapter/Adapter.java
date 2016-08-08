package com.gems.adapter;

import com.gems.event.ProgressListener;
import com.gems.model.Progress;
import com.gems.util.ConfigFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by nayana on 8/6/16.
 */
public interface Adapter
{
    File download() throws IOException;

    void setOnProgressListener(ProgressListener progressListener);
}
