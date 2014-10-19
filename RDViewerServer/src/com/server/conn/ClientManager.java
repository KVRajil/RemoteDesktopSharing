/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.conn;

import com.bean.ClientInformations;
import com.bean.MessageBean;
import com.server.gui.ChatWithClientJFrame;
import com.server.gui.ClientPanel;
import com.server.utils.MessageInfo;
import com.server.utils.SettingsConstatnts;
import java.awt.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class ClientManager implements Runnable {

    private Socket clientSocket = null;
    private String ipAddress = "";

    public ClientManager(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.ipAddress = clientSocket.getInetAddress().getHostAddress();
    }

    public void run() {
        try {
            sendRequestToClient("TIME:"+ SettingsConstatnts.CAPTURING_INRVL);
            while (true) {
                ObjectInputStream readFromClient = new ObjectInputStream(
                        clientSocket.getInputStream());
                Object readData = readFromClient.readObject();
                processClientData(readData);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            if (ConnectionManager.chatClients.containsKey(getIpAddress())) {
                ChatWithClientJFrame chatWithClientJFrame =
                        ConnectionManager.chatClients.get(getIpAddress());
                chatWithClientJFrame.close();
                ConnectionManager.chatClients.remove(getIpAddress());
            }
            ConnectionManager.connectedClients.remove(getIpAddress());
            ConnectionManager.panelManager.setDeafualtPanel(getIpAddress());
            System.out.println(ex.getMessage());
        }
    }

    private void processClientData(Object readData) {
        if (readData instanceof ClientInformations) {
            ClientInformations clientInformations = (ClientInformations) readData;
            clientInformations.setIpAddress(ipAddress);
            ClientPanel clientPanel =
                    ConnectionManager.panelManager.getClientPanel(clientInformations.getIpAddress());
            if (clientPanel != null) {
                System.out.println("os name : " + clientInformations.getOsName());
                clientPanel.setHostName(clientInformations.getHostName());
                clientPanel.setOsName(clientInformations.getOsName());
                clientPanel.setOsVersion(clientInformations.getOsVersion());
                clientPanel.setOsArch(clientInformations.getOsArch());
                clientPanel.setCurrentUserNeame(clientInformations.getCurrentUser());
            } 
        } else if (readData instanceof MessageBean) {
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setIpAddress(getIpAddress());
            messageInfo.setSubject(((MessageBean) readData).getSubject());
            messageInfo.setMessageBody(((MessageBean) readData).getMessage());
            messageInfo.setTime(new Date());
            SettingsConstatnts.messages.addElement(messageInfo);
        } else if (readData instanceof ImageIcon) {
            ConnectionManager.desktopManager.setDesktopImage((ImageIcon) readData,
                    clientSocket.getInetAddress().getHostAddress());
        } else if (readData instanceof String) {
            ChatWithClientJFrame chatWithClientJFrame =
                    ConnectionManager.chatClients.get(getIpAddress());
            if (chatWithClientJFrame == null) {
                chatWithClientJFrame = new ChatWithClientJFrame(this);
                chatWithClientJFrame.setLocationRelativeTo(null);
                ConnectionManager.chatClients.put(getIpAddress(), chatWithClientJFrame);
            }
            chatWithClientJFrame.setVisible(true);
            chatWithClientJFrame.setTitle("Chat With - " + ipAddress);
            chatWithClientJFrame.setState(Frame.NORMAL);
            if (((String) readData).equals("VOICE_CHAT")) {
                if (JOptionPane.showConfirmDialog(chatWithClientJFrame,
                        "A Voice chat request from "
                        + getIpAddress() + " \n Do you want accept?",
                        "Voice Chat", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    chatWithClientJFrame.startTalk();
                } else {
                    sendRequestToClient("ERROR:" + "Server reject your chat request");
                }
            } else {
                chatWithClientJFrame.viewSendReceiveMsg((String) readData);
            }
        }
    }

    public void sendRequestToClient(Object sendData) {
        if (clientSocket != null) {
            try {
                ObjectOutputStream writeToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                writeToClient.writeObject(sendData);
                writeToClient.flush();
                
            } catch (IOException ex) {
                Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
