package com.client.filetransfer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
*/


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ListernForFileReceive implements Runnable {
    public static final int FILE_TRANSFER_PORT = 8080;
    ServerSocket fileServerSocket = null;    

    public ListernForFileReceive() {
        try {
            fileServerSocket = new ServerSocket(FILE_TRANSFER_PORT);
        } catch (IOException ex) {
            Logger.getLogger(ListernForFileReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {
            try {
                System.out.println("waiting for filr receive");
                Socket fileSocket = fileServerSocket.accept();
                System.out.println("Connected");
                FileReceiver fileReceiver = new FileReceiver(fileSocket);
                new Thread(fileReceiver).start();
            } catch (IOException ex) {
                Logger.getLogger(ListernForFileReceive.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
