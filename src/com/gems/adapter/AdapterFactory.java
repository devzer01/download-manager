package com.gems.adapter;

import com.gems.exception.AdapterNotFoundException;

import com.gems.model.Task;
/**
 * Created by nayana on 8/6/16.
 *
 * a simple factory that produce a GenericAdapter or SftpAdapter or that throws AdapterNotFoundException
 * more details on Adapter is written on Adapter interface comments
 */
public class AdapterFactory
{
    /**
     *
     * @param task com.gems.model.Task
     * @return Adapter
     * @throws AdapterNotFoundException
     */
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
