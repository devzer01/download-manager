package com.gems.adapter;

import com.gems.event.ProgressListener;
import com.gems.model.Progress;
import com.gems.util.ConfigFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;


/**
 * Created by nayana on 8/6/16.
 */
public class GenericAdapter implements Adapter
{
    protected ArrayList<ProgressListener> progressListeners = new ArrayList<ProgressListener>();

    protected URL url;

    protected URLConnection urlConnection;

    public GenericAdapter(URL url)
    {
        this.url = url;
    }

    public void setOnProgressListener(ProgressListener progressListner)
    {
        this.progressListeners.add(progressListner);
    }

    protected void onProgress()
    {
        for (ProgressListener progressListener : this.progressListeners) {
            progressListener.onProgressEvent();
        }
    }

    public void download(Progress progress, ConfigFile configFile) throws IOException
    {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        Path path = Paths.get(url.getFile());
        String tempFileName = configFile.getTempDir() + "/" + path.getFileName().toString();
        try {

            urlConnection = url.openConnection();
            inputStream = urlConnection.getInputStream();
            progress.size = urlConnection.getContentLengthLong();

            fileOutputStream = new FileOutputStream(tempFileName);

            int bytesRead = -1;
            byte[] buffer = new byte[4096];

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                progress.status = "downloading...";
                this.onProgress();
            }

            progress.status = "finished";

            File fileTemp = new File(tempFileName);
            fileTemp.renameTo(new File(configFile.getDownloadFolder() + "/" + path.getFileName().toString()));

        } catch (IOException e) {
            progress.status = "error";
            File fileTemp = new File(tempFileName);
            fileTemp.delete();
        } finally {
            try {
                inputStream.close();
                fileOutputStream.close();
                this.onProgress();
            } catch (IOException|NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
