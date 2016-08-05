package com.gems;

import com.gems.exception.InvalidUrlException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nayana on 8/5/16.
 */
public class URLDepricated {

    protected String url = null;
    protected String scheme = null;

    private static Pattern schemePattern = Pattern.compile("^[^:]+");

    public URLDepricated(String url) {
        this.url = url;
    }

    public String getScheme() throws InvalidUrlException
    {
        if (this.scheme == null) {
            Matcher matcher = URLDepricated.schemePattern.matcher(this.url);
            matcher.find();
            this.scheme = matcher.group(0);
            if (this.scheme == this.url) {
                this.scheme = null;
                throw new InvalidUrlException(); //since we validate prior to creating URLDepricated this should not happen
            }
        }

        return this.scheme;
    }
}
