package com.gems.adapter;

import com.gems.util.DownloadableFile;
import com.gems.event.ProgressListener;

import java.io.IOException;

/**
 * Created by nayan on 8/6/16.
 */
public interface Adapter
{
    void download(DownloadableFile downloadableFile, String downloadFolder) throws IOException;

    void setOnProgressListener(ProgressListener progressListener);
}
