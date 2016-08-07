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
import com.gems.protocol.sftp.SftpURLConnection;
import com.gems.util.ConfigFile;

/**
 * Created by nayana on 8/6/16.
 */
public class SftpAdapter extends GenericAdapter
{

    public SftpAdapter(URL url)
    {
        super(url);
    }

    public void download(Progress progress, ConfigFile configFile) throws IOException
    {
        super.download(progress, configFile);
        ((SftpURLConnection) urlConnection).disconnect();
    }
}
