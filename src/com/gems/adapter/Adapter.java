package com.gems.adapter;

import java.net.URL;
import org.apache.commons.validator.routines.DomainValidator;

/**
 * Created by nayana on 8/5/16.
 */
abstract public class Adapter
{

    URL url = null;

    public Adapter(URL url)
    {
        this.url = url;
    }

    protected boolean validateHost()
    {
        DomainValidator domainValidator = DomainValidator.getInstance();
        return (domainValidator.isValid(url.getHost()));
    }

    abstract public boolean validate();

    abstract public void download();
}
