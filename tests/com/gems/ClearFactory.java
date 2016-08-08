package com.gems;

import java.lang.reflect.Field;
import java.net.URL;

/**
 * Created by nayan on 8/8/16.
 */
public class ClearFactory
{
    public static void clearFactory()
    {
        try {
            final Field factoryField = URL.class.getDeclaredField("factory");
            factoryField.setAccessible(true);
            factoryField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error("Could not access factory field on URL class: {}", e);
        }
    }
}
