package com.gems.adapter;

import com.gems.util.DownloadableFile;
import com.gems.event.ProgressListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by nayana on 8/6/16.
 */
public class GenericAdapter implements Adapter
{
    protected ArrayList<ProgressListener> progressListeners = new ArrayList<ProgressListener>();

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

    public void download(DownloadableFile downloadableFile, String downloadFolder) throws IOException
    {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            URLConnection con = downloadableFile.getUrl().openConnection();
            inputStream = con.getInputStream();

            //TODO: write to tempfile and move when done
            fileOutputStream = new FileOutputStream(downloadFolder + "/" + downloadableFile.getUrl().getFile());

            int bytesRead = -1;
            byte[] buffer = new byte[4096];

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                downloadableFile.getProgress().status = "downloading...";
                this.onProgress();
            }

            downloadableFile.getProgress().status = "finished";

        } catch (IOException e) {
            downloadableFile.getProgress().status = "error";
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
