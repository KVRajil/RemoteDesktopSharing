/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.monitor;

import com.bean.ActionRequestBean;
import com.server.conn.ClientManager;
import com.server.conn.ConnectionManager;
import com.server.gui.ClientDesktop;
import com.server.gui.ClientPanel;
import com.server.utils.ImageHandler;
import com.server.utils.SettingsConstatnts;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class ClientDesktopManager {

    private String fullScreenIp = "";
    ClientDesktop clientDesktop = null;
    ImageHandler imageHandler = null;

    public ClientDesktopManager() {
        imageHandler = new ImageHandler();
    }

    synchronized public void setDesktopImage(ImageIcon imageIcon, String ipAddress) {
        if (SettingsConstatnts.SAVE_IMAGE) {
            imageHandler.saveImage(imageIcon, ipAddress);
        }
        if (ipAddress.equals(fullScreenIp)) {
            if (clientDesktop != null) {
                clientDesktop.setDesktopImage(imageIcon);
                clientDesktop.setIpAddress(ipAddress);
            }
        } else {
            Image image = imageIcon.getImage();
            int imgWidth = image.getWidth(null);
            int imgHeight = image.getHeight(null);
            int labelWidth = 148;
            int labelHeight = 83;
            imageIcon = imageHandler.getScaledImage(image,
                    imgWidth, imgHeight, labelWidth, labelHeight);
            ConnectionManager.panelManager.setClientImage(imageIcon, ipAddress);
        }
    }

    /**
     * @return the currentIp
     */
    public String getCurrentIp() {
        return fullScreenIp;
    }

    /*
     * @param currentIp the currentIp to set
     */
//    public void setCurrentIp(String fullScreenIp) {
//        this.fullScreenIp = fullScreenIp;
//    }
    public void viewFullScreen(String fullScreenIp) {
        int option = JOptionPane.OK_OPTION;
        if (!this.fullScreenIp.equals("")) {
            option = JOptionPane.showConfirmDialog(null, "", "View Full Screen",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        if (option == JOptionPane.OK_OPTION) {
            this.fullScreenIp = fullScreenIp;
            ClientManager clientManager =
                    (ClientManager) ConnectionManager.connectedClients.get(fullScreenIp);
            if (clientManager != null) {
                clientManager.sendRequestToClient("GET_FULL_SCREEN");
            }
            if (clientDesktop == null) {
                clientDesktop = new ClientDesktop();
            }
            clientDesktop.setLocationRelativeTo(null);
            clientDesktop.setVisible(true);
        }
    }

    public void closeFullScreen(String ipAddress) {
        this.fullScreenIp = "";
        ClientManager clientManager =
                (ClientManager) ConnectionManager.connectedClients.get(ipAddress);
        if (clientManager != null) {
            clientManager.sendRequestToClient("STOP_FULL_SCREEN");
            clientManager.sendRequestToClient("TIME:" + SettingsConstatnts.CAPTURING_INRVL);
        }
    }

    public void mouseMoved(int x, int y, String ipAddress) {
        ClientManager clientManager =
                (ClientManager) ConnectionManager.connectedClients.get(ipAddress);
        if (clientManager != null) {
            ActionRequestBean actionRequestBean = new ActionRequestBean();
            actionRequestBean.setAction("MOUSE_MOVE");
            actionRequestBean.setxCord(x);
            actionRequestBean.setyCord(y);
            clientManager.sendRequestToClient(actionRequestBean);
        }
    }

    public void mousePressed(String ipAddress, MouseEvent mouseEvent) {
        ClientManager clientManager =
                (ClientManager) ConnectionManager.connectedClients.get(ipAddress);
        if (clientManager != null) {
            ActionRequestBean actionRequestBean = new ActionRequestBean();
            actionRequestBean.setClickCount(mouseEvent.getClickCount());
            actionRequestBean.setMouseButton(mouseEvent.getButton());
            actionRequestBean.setAction("MOUSE_PRESSED");
            clientManager.sendRequestToClient(actionRequestBean);
        }
    }

    public void mouseReleased(String ipAddress, MouseEvent mouseEvent) {
        ClientManager clientManager =
                (ClientManager) ConnectionManager.connectedClients.get(ipAddress);
        if (clientManager != null) {
            ActionRequestBean actionRequestBean = new ActionRequestBean();
            actionRequestBean.setClickCount(mouseEvent.getClickCount());
            actionRequestBean.setMouseButton(mouseEvent.getButton());
            actionRequestBean.setAction("MOUSE_RELEASED");
            clientManager.sendRequestToClient(actionRequestBean);
        }
    }

    public void removeDesktop(String ipAddress) {
    }

    public void keyPressed(int keyCode, String ipAddress) {
        ClientManager clientManager =
                (ClientManager) ConnectionManager.connectedClients.get(ipAddress);
        if (clientManager != null) {
            ActionRequestBean actionRequestBean = new ActionRequestBean();
            actionRequestBean.setAction("KEY_PRESSED");
            actionRequestBean.setKeyCode(keyCode);
            clientManager.sendRequestToClient(actionRequestBean);
        }
    }

    public void keyReleased(int keyCode, String ipAddress) {
        ClientManager clientManager =
                (ClientManager) ConnectionManager.connectedClients.get(ipAddress);
        if (clientManager != null) {
            ActionRequestBean actionRequestBean = new ActionRequestBean();
            actionRequestBean.setAction("KEY_RELEASED");
            actionRequestBean.setKeyCode(keyCode);
            clientManager.sendRequestToClient(actionRequestBean);
        }
    }
}
