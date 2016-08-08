package com.gems.util;

import org.ini4j.Config;
import org.ini4j.Ini;
import org.junit.Test;
import java.util.prefs.Preferences;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by nayan on 8/7/16.
 */
public class ConfigFileTest {

    @Test
    public void getDownloadFolder() throws Exception {
        ConfigFile configFile = new ConfigFile();
        assertNotNull(configFile.getDownloadFolder());
    }
}