package com.gems.util;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Created by nayana on 8/5/16.
 */
public class ConfigFile {

    private static String defaultConfig = "resources/config.ini";

    private static ConfigFile configFile = null;

    private Preferences prefs = null;

    private ConfigFile(Preferences prefs)
    {

        this.prefs = prefs;
    }

    public String getDownloadFolder()
    {

        return this.prefs.node("main").get("download_path", null);
    }

    public static ConfigFile parse(String configFile) throws IOException
    {
        if (ConfigFile.configFile == null) {
            if (configFile == null) {
                configFile = ConfigFile.defaultConfig;
            }
            Ini ini = null;
            try {
                ini = new Ini(new File(configFile));
            } catch (IOException e) {
                //do something here
            }
            ConfigFile.configFile = new ConfigFile(new IniPreferences(ini));
        }

        return ConfigFile.configFile;
    }

}
