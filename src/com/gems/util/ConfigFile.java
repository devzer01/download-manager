package com.gems.util;

import com.sun.xml.internal.stream.Entity;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Created by nayana on 8/5/16.
 */
public class ConfigFile {

    protected String defaultConfig = "resources/config.ini";

    protected Preferences prefs = null;

    public ConfigFile() throws IOException
    {
        Ini ini = null;
        ini = new Ini(new File(defaultConfig));
        this.prefs = new IniPreferences(ini);
    }

    public String getDownloadFolder()
    {
        return this.prefs.node("main").get("download_path", null);
    }

    public String getProgressFormatter()
    {
        return this.prefs.node("main").get("progress_formatter", null);
    }

    public static String getTempDir() {
        return System.getProperty("java.io.tmpdir");
    }

    public int getThreadCount()
    {
        return Integer.parseInt(this.prefs.node("main").get("thread_count", String.valueOf(1)));
    }
}
