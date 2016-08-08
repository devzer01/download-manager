package com.gems.adapter;

import com.gems.event.ProgressListener;
import com.gems.model.Progress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.gems.model.Task;
import com.gems.protocol.sftp.DisconnectStream;
import com.gems.protocol.sftp.SftpURLConnection;
import com.gems.util.ConfigFile;
import java.io.File;

/**
 * Created by nayana on 8/6/16.
 */
public class SftpAdapter extends GenericAdapter
{
    protected int defaultBufferSize = 4096;

    protected int defaultSocketTimeout = 10000; //10 seconds for timeout

    protected org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SftpAdapter.class.getName());

    public SftpAdapter(Task task)
    {
        super(task);
    }

    public File download() throws IOException
    {
        File file = super.download();
        ((DisconnectStream) urlConnection).disconnect();
        return file;
    }
}
