package com.gems.adapter;

import com.gems.exception.AdapterNotFoundException;

import com.gems.model.Task;
/**
 * Created by nayana on 8/6/16.
 */
public class AdapterFactory
{
    public static Adapter getAdapter(Task task) throws AdapterNotFoundException
    {
        Adapter adapter = null;
        String protocol = task.getUrl().getProtocol();
        if (protocol.equals("http") || protocol.equals("ftp")) {
            adapter = new GenericAdapter(task);
        } else if(protocol.equals("sftp")) {
            adapter = new SftpAdapter(task);
        } else {
            throw new AdapterNotFoundException();
        }
        adapter.setOnProgressListener(task.getProgressIndicator());
        return adapter;
    }
}
