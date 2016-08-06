package com.gems.adapter;

import com.gems.DownloadableFile;
import com.gems.Progress;
import com.gems.event.ProgressListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
            //this logic needs to be moved to some sort of interface
            //so that we can handle both single and multi connection protocols
            URLConnection con = downloadableFile.url.openConnection();
            System.out.println(con.getClass().getName());
            inputStream = con.getInputStream();

            //TODO: write to tempfile and move when done
            fileOutputStream = new FileOutputStream(downloadFolder + "/" + downloadableFile.url.getFile());

            int bytesRead = -1;
            byte[] buffer = new byte[4096];

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                downloadableFile.progress.status = "downloading...";
                this.onProgress();
            }

            downloadableFile.progress.status = "finished";

        } catch (IOException e) {
            downloadableFile.progress.status = "error";
        } finally {
            try {
                inputStream.close();
                fileOutputStream.close();
                this.onProgress();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (java.lang.NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
