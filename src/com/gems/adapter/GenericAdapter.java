package com.gems.adapter;

import com.gems.model.DownloadableFile;
import com.gems.event.ProgressListener;
import com.gems.model.Progress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nayana on 8/6/16.
 */
public class GenericAdapter implements Adapter
{
    protected ArrayList<ProgressListener> progressListeners = new ArrayList<ProgressListener>();

    protected URL url;

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

    public void download(Progress progress, String downloadFolder) throws IOException
    {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            URLConnection con = url.openConnection();
            inputStream = con.getInputStream();

            //TODO: write to tempfile and move when done
            fileOutputStream = new FileOutputStream(downloadFolder + "/" + url.getFile());

            int bytesRead = -1;
            byte[] buffer = new byte[4096];

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                progress.status = "downloading...";
                this.onProgress();
            }

            progress.status = "finished";

        } catch (IOException e) {
            progress.status = "error";
        } finally {
            try {
                inputStream.close();
                fileOutputStream.close();
                this.onProgress();
            } catch (IOException e) {
                //System.out.println(e.getMessage());
            } catch (java.lang.NullPointerException e) {
                //System.out.println(e.getMessage());
            }
        }
    }
}
