/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.conn;

import com.server.gui.ChatWithClientJFrame;
import com.server.gui.mngr.PanelManager;
import com.server.gui.ServerMainForm;
import com.server.monitor.ClientDesktopManager;
import com.server.voicechat.VoiceChat;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ConnectionManager implements Runnable {

    public static boolean serverStarted = false;
    public static final int SERVER_PORT = 9000;
    public static Hashtable connectedClients = new Hashtable();
    public static Hashtable<String, ChatWithClientJFrame> chatClients =
            new Hashtable<String, ChatWithClientJFrame>();
    private ServerSocket serverSocket = null;
    private ServerMainForm mainForm = null;
    public static PanelManager panelManager = null;
    public static ClientDesktopManager desktopManager = null;
    public static VoiceChat voiceChat = null;
    public ConnectionManager(ServerMainForm mainForm) {
        this.mainForm = mainForm;
        panelManager = new PanelManager(mainForm);
        desktopManager = new ClientDesktopManager();
    }

    public void startServer() {
        serverStarted = true;
        Thread connectionThread = new Thread(this);
        connectionThread.start();
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            while (serverStarted) {
                listenForClientConnection();
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listenForClientConnection() {
        try {
            Socket clientSocket = serverSocket.accept();
            String clientAddress = clientSocket.getInetAddress().getHostAddress();
            System.out.println(clientAddress);
            ClientManager clientManager = new ClientManager(clientSocket);
            Thread clientThread = new Thread(clientManager);

            connectedClients.put(clientAddress, clientManager);
            panelManager.addClientPanel(clientAddress);
            clientThread.start();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
