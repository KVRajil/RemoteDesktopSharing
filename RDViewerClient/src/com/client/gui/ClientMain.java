/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.gui;

import com.client.conn.ConnectionManager;
import com.client.filetransfer.FileReceiver;
import com.client.filetransfer.ListernForFileReceive;
import com.client.voicechat.ListenForVoiceChat;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ClientMain {

    private String trayIcomImagePath = "/com/client/icons/systemtray.PNG";
    private TrayIcon trayIcon = null;
    private PopupMenu popupMenu = null;
    private MenuItem sendMessageMenuItem = null;
    //private MenuItem aboutMenuItem = null;
    private MenuItem exitMenuItem = null;
    private MenuItem chatMenuItem = null;
    private MenuItem settingsMenuItem = null;
    private SendMessageDialog messageDialog = null;
    private SetFilePathDialog filePathDialog = null;
    public ConnectionManager connectionManager = null;
    public ChatWithServerJFrame chatWithServerJFrame = null;
    public ListenForVoiceChat listenForVoiceChat = null;

    public ClientMain() {
        initComponents();
        connectionManager = new ConnectionManager(this);
        connectionManager.startClient();
        listenForVoiceChat = new ListenForVoiceChat(this);
        new Thread(listenForVoiceChat).start();
        ListernForFileReceive fileReceive = new ListernForFileReceive();
        new Thread(fileReceive).start();
    }

    private void initComponents() {
        messageDialog = new SendMessageDialog(this);
        messageDialog.setLocationRelativeTo(null);
        filePathDialog = new SetFilePathDialog();
        filePathDialog.setLocationRelativeTo(null);
        chatWithServerJFrame = new ChatWithServerJFrame(this);
        chatWithServerJFrame.setLocationRelativeTo(null);
        if (SystemTray.isSupported()) {
            try {
                SystemTray systemTray = SystemTray.getSystemTray();
                Image trayIcomImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource(trayIcomImagePath));
                popupMenu = new PopupMenu();

                chatMenuItem = new MenuItem("Chat with Administrator...");
                chatMenuItem.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        chatWithServerJFrame.setState(Frame.NORMAL);
                        chatWithServerJFrame.setVisible(true);
                    }
                });
                popupMenu.add(chatMenuItem);
                sendMessageMenuItem = new MenuItem("Send Message To Admin...");
                sendMessageMenuItem.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        messageDialog.setState(Frame.NORMAL);
                        messageDialog.setVisible(true);
                    }
                });
                popupMenu.add(sendMessageMenuItem);
                // aboutMenuItem = new MenuItem("About");
                // popupMenu.add(aboutMenuItem);
                settingsMenuItem = new MenuItem("Settings");
                settingsMenuItem.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        filePathDialog.setState(Frame.NORMAL);
                        filePathDialog.setVisible(true);
                    }
                });
                popupMenu.add(settingsMenuItem);
                exitMenuItem = new MenuItem("Exit");
                exitMenuItem.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        ConnectionManager.serverStarted = false;
                        System.exit(0);
                    }
                });
                popupMenu.add(exitMenuItem);
                trayIcon = new TrayIcon(trayIcomImage, "RDViewer Client", popupMenu);
                trayIcon.setImageAutoSize(true);
                systemTray.add(trayIcon);
            } catch (AWTException ex) {
                Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        new ClientMain();
    }
}
