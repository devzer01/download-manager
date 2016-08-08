package com.gems.adapter;

import com.gems.event.ProgressListener;
import com.gems.model.Progress;
import com.gems.model.Status;
import com.gems.model.Task;
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
    protected int defaultBufferSize = 4096;

    protected int defaultSocketTimeout = 10000; //10 seconds for timeout

    protected ArrayList<ProgressListener> progressListeners = new ArrayList<ProgressListener>();

    protected Task task;

    protected org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(GenericAdapter.class.getName());

    protected URLConnection urlConnection;

    public GenericAdapter(Task task)
    {
        this.task = task;
    }

    public void setOnProgressListener(ProgressListener progressListner)
    {
        this.progressListeners.add(progressListner);
    }

    protected void onProgress()
    {
        progressListeners.forEach((progressListener) -> progressListener.onProgressEvent());
    }

    public File download() throws IOException
    {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        Path path = Paths.get(task.getUrl().getFile());
        String tempFileName = ConfigFile.getTempDir() + File.separator + path.getFileName().toString();
        try {

            urlConnection = task.getUrl().openConnection();
            urlConnection.setConnectTimeout(defaultSocketTimeout);
            inputStream = urlConnection.getInputStream();

            task.getProgress().size = urlConnection.getContentLengthLong();

            fileOutputStream = new FileOutputStream(tempFileName);

            int bytesRead = -1;
            byte[] byteBuffer = new byte[defaultBufferSize];

            task.getProgress().status = Status.DOWNLOADING;

            while ((bytesRead = inputStream.read(byteBuffer)) != -1) {
                fileOutputStream.write(byteBuffer, 0, bytesRead);
                this.onProgress();
            }

            task.getProgress().status = Status.DONE;

        } catch (IOException e) {
            task.getProgress().status = Status.ERROR;
        } finally {
            try {
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException|NullPointerException e) {
                log.debug("error when closing stream");
            }
        }

        this.onProgress();

        return new File(tempFileName);
    }
}
