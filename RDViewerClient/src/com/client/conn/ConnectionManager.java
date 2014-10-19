/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.conn;

import com.bean.ActionRequestBean;
import com.client.gui.ClientMain;
import com.client.util.Utility;
import java.awt.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class ConnectionManager implements Runnable {

    public static String serverIP = "";
    public static boolean serverStarted = false;
    public static final int SERVER_PORT = 9000;
    Socket clientSocket = null;
    public static boolean notConneted = true;
    RequestHandler requestHandler = null;
    ClientMain clientMain = null;
    private final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public ConnectionManager(ClientMain clientMain) {
        this.clientMain = clientMain;

        requestHandler = new RequestHandler();

    }

    public void startClient() {

        Thread clientThread = new Thread(this);
        clientThread.start();
    }

    public void run() {
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        while (notConneted) {
            try {
                String ipString = JOptionPane.showInputDialog(null, "IP Address", "");
                if (ipString == null) {
                    System.exit(0);
                } else {
                    Matcher matcher = pattern.matcher(ipString);
                    if (matcher.find()) {
                        clientSocket = new Socket(ipString, SERVER_PORT);
                        sendDataToServer(new Utility().getSystemInformations());
                        notConneted = false;

                        serverStarted = true;
                        DesktopImageManger desktopImageManger = new DesktopImageManger(this);
                        Thread sendScreenThread = new Thread(desktopImageManger);
                        sendScreenThread.start();
                        while (serverStarted) {
                            ObjectInputStream readFromClient = new ObjectInputStream(
                                    clientSocket.getInputStream());
                            Object readData = readFromClient.readObject();
                            processClientData(readData);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid IP Address", "Invalid IP", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(null, "Falied to Connect to server",
                        "Connect to Server", JOptionPane.ERROR_MESSAGE);
                //  System.exit(0);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Connect to Server", JOptionPane.ERROR_MESSAGE);
                //  System.exit(0);
            }
        }
    }

    public synchronized void sendDataToServer(Object data) throws IOException {
        if (clientSocket != null) {
            ObjectOutputStream writeToServer = new ObjectOutputStream(
                    clientSocket.getOutputStream());
            writeToServer.writeObject(data);
            writeToServer.flush();
        }
    }

    private void processClientData(Object readData) {
        if (readData instanceof String) {
            String data = (String) readData;
            try {
                if (data.startsWith("MESSAGE:")) {
                    clientMain.chatWithServerJFrame.viewSendReceiveMsg(data.substring(7));
                    clientMain.chatWithServerJFrame.setVisible(true);
                    clientMain.chatWithServerJFrame.setState(Frame.NORMAL);
                } else if (data.startsWith("ERROR:")) {
                    clientMain.chatWithServerJFrame.viewErreorMsg(data.substring(6));
                    clientMain.chatWithServerJFrame.setVisible(true);
                    clientMain.chatWithServerJFrame.setState(Frame.NORMAL);
                } else if (data.startsWith("TIME:")) {

                    String msg = data.trim();
                    String time = msg.substring(msg.indexOf(":") + 1, msg.length());
                    System.out.println("..." + time + "....");
                    try {
                        int parseInt = Integer.parseInt(time);
                        if (parseInt == 0) {
                            DesktopImageManger.delay = 100;
                        } else {
                            DesktopImageManger.delay = 1000 * parseInt * 60;
                            System.out.println("Time Changed");
                        }
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                } else if (data.startsWith("ALERT:")) {

                    String msg = data.trim();
                    String info = msg.substring(msg.indexOf(":") + 1, msg.length());
                    String title = info.substring(0, info.indexOf("|"));
                    String content = info.substring(info.indexOf("|") + 1, info.length());
                    JOptionPane.showMessageDialog(null, content, "Alert From Admin : " + title, JOptionPane.WARNING_MESSAGE);

                }
            } catch (StringIndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
            requestHandler.processServerRequest((String) readData);
        } else if (readData instanceof ActionRequestBean) {
            requestHandler.actionHandler((ActionRequestBean) readData);
        }
    }
}
