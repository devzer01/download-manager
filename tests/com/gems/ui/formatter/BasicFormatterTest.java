package com.gems.ui.formatter;

import com.gems.model.Progress;
import org.junit.Test;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by nayan on 8/7/16.
 */
public class BasicFormatterTest {
    @Test
    public void format() throws Exception {
        BasicFormatter basicFormatter = new BasicFormatter();
        String formattedString = basicFormatter.format(new URL("http://www.google.com/foobar"), new Progress("init"));
        assertEquals("/foobar - init - 0\n", formattedString);
    }

}