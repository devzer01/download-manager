package com.gems;

import com.gems.model.*;
import com.gems.ui.ConsoleProgressIndicator;
import com.gems.ui.ProgressIndicator;
import com.gems.ui.formatter.BasicFormatter;
import com.gems.util.ConfigFile;
import com.gems.util.Filename;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.File;

/**
 * Created by nayana on 8/7/16.
 *
 * functional test
 */
public class DownloadManagerTest {
    @Before
    public void setUp() throws Exception {

        ConfigFile configFile = new ConfigFile();
        try {
            Files.newDirectoryStream(Paths.get(configFile.getDownloadFolder())).forEach(
                    file -> {
                        try { Files.delete( file ); }
                        catch ( IOException e ) { throw new UncheckedIOException(e); }
            } );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void download() throws Exception {
        DownloadList downloadList = new DownloadList();
        URL url = new URL("http://www.slbeat.com/twistd.log");
        Task task = new Task(url, new Progress(Status.INIT));
        downloadList.put("foobar", task);
        ProgressIndicator progressIndicator = new ConsoleProgressIndicator(new BasicFormatter());
        Job job = new Job(downloadList, progressIndicator);
        ConfigFile configFile = new ConfigFile();
        DownloadManager downloadManager = new DownloadManager(job, configFile);
        downloadManager.startDownload();
        File file = new File(configFile.getDownloadFolder() + File.separator + Filename.UrlToFilename(url));
        assertTrue(file.exists());
    }

    @Test
    public void setDownloadFolder() throws Exception {

    }

}