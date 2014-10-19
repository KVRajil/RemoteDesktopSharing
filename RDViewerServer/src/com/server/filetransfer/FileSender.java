package com.server.filetransfer;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.File;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSender implements Runnable {

    String sourceFilePath = "";
    String destinationFilePath = "";
    String host = "";
    boolean delete = false;
    Socket socket = null;

    public FileSender(Socket socket, String sourceFilePath, String host, boolean delete) {
        this.sourceFilePath = sourceFilePath;
        this.host = host;
        this.delete = delete;
        this.socket = socket;
        this.destinationFilePath = sourceFilePath.substring(sourceFilePath.lastIndexOf(File.separator) + 1);
    }

    public FileSender(Socket socket, String sourceFilePath,
            String destinationFilePath, String host, boolean delete) {
        this.socket = socket;
        this.sourceFilePath = sourceFilePath;
        this.host = host;
        this.delete = delete;
        this.destinationFilePath = destinationFilePath;
    }

    @Override
    public void run() {
        try {
            OutputStream os = socket.getOutputStream();
            ByteStream.toStream(os, 1);
            ByteStream.toStream(os, destinationFilePath);
            ByteStream.toStream(os, new File(sourceFilePath));
            if (delete) {
                if (new File(sourceFilePath).delete()) {
                    System.out.println("File Deleted Successfully");
                } else {
                    System.out.println("File Deletion Failed");
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}