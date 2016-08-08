package com.gems;

import com.gems.adapter.MockedStreamHandlerFactory;
import com.gems.model.*;
import com.gems.ui.ConsoleProgressIndicator;
import com.gems.ui.ProgressIndicator;
import com.gems.ui.formatter.BasicFormatter;
import com.gems.util.ConfigFile;
import com.gems.util.Filename;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * Created by nayana on 8/7/16.
 *
 * functional test
 */
public class DownloadManagerFailTest extends DownloadManagerTest {
    @Before
    public void setUp() throws Exception {
        super.setUp();
        URLStreamHandlerFactory factory = new MockedStreamHandlerFactory();
        try {
            URL.setURLStreamHandlerFactory(factory);
        } catch (final Error e) {

        }
    }

    @After
    public void tearDown() throws Exception {
        ClearFactory.clearFactory();
    }

    /**
     * we use the mock factory set on sftp to test the faliure
     * @throws Exception
     */
    @Test
    public void download() throws Exception {
        DownloadList downloadList = new DownloadList();
        URL url = new URL("sftp://www.slbeat.com/twistd.log");
        Task task = new Task(url, new Progress(Status.INIT));
        downloadList.put("foobar", task);
        ProgressIndicator progressIndicator = new ConsoleProgressIndicator(new BasicFormatter());
        Job job = new Job(downloadList, progressIndicator);
        ConfigFile configFile = new ConfigFile();
        DownloadManager downloadManager = new DownloadManager(job, configFile);
        downloadManager.startDownload();
        File file = new File(configFile.getDownloadFolder() + File.separator + Filename.UrlToFilename(url));
        assertTrue(!file.exists());
    }
}