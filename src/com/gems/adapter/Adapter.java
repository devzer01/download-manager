package com.gems.adapter;

import com.gems.event.ProgressListener;

import java.io.File;
import java.io.IOException;

/**
 * Created by nayana on 8/6/16.
 *
 * this interface is used to encapsulate protocol logic,
 * known implementations are GenericAdapter and SftpAdapter
 * goal is to keep the driver interface simple, when dealing with a complex protocol such as
 * torrent multiple input streams will be at play and it will not be possible to expose a
 * stream directly to the driver, most standard protocols can be handled with
 * GenericAdapter, only difference in SftpAdapter is that it needs to disconnect the ssh connection and the
 * Sftp channel ontop of ssh
 */
public interface Adapter
{
    File download() throws IOException;

    void setOnProgressListener(ProgressListener progressListener);
}
