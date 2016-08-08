package com.gems.model;

/**
 * Created by nayana on 8/6/16.
 */
public class Progress {
    public Status status;

    public long size = 0;

    public long currentSize = 0;

    public Progress (Status status)
    {
        this.status = status;
    }
}
