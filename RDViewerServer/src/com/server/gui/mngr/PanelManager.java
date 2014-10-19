/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.gui.mngr;

import com.server.gui.*;
import java.awt.Color;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

/**
 *
 * @author Administrator
 */
public class PanelManager {

    ServerMainForm mainForm = null;
    Vector clientPanels = new Vector();
    ClientPanel selectedClientPanel = null;

    public PanelManager(ServerMainForm mainForm) {
        this.mainForm = mainForm;
        for (int i = 0; i < 20; i++) {
            addClientPanel("");
        }
    }

    public void addClientPanel(String ipAddress) {
        if (ipAddress.equals("") || !isFreePanelAvailable()) {
            ClientPanel clientPanel = new ClientPanel();
            clientPanel.setIpAddress(ipAddress);
            clientPanels.addElement(clientPanel);
            mainForm.addClientPanel(clientPanel);
        } else {
            ClientPanel clientPanel = getFreePanel();
            if (clientPanel != null) {
                clientPanel.setIpAddress(ipAddress);
                clientPanel.setFree(false);
            }
        }
    }

    private boolean isFreePanelAvailable() {
        for (int i = 0; i < clientPanels.size(); i++) {
            ClientPanel clientPanel = (ClientPanel) clientPanels.elementAt(i);
            if (clientPanel.isFree()) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    private ClientPanel getFreePanel() {
        for (int i = 0; i < clientPanels.size(); i++) {
            ClientPanel clientPanel = (ClientPanel) clientPanels.elementAt(i);
            if (clientPanel.isFree()) {
                return clientPanel;
            }
        }
        return null;
    }

    public void setClientImage(ImageIcon image, String ipAddress) {
        ClientPanel clientPanel = getClientPanel(ipAddress);
        if (clientPanel != null) {
            clientPanel.setClientDesktop(image);
        }
    }

    public ClientPanel getClientPanel(String ipAddress) {
        for (int i = 0; i < clientPanels.size(); i++) {
            ClientPanel clientPanel = (ClientPanel) clientPanels.elementAt(i);
            if (clientPanel.getIpAddress().trim().equals(ipAddress)) {
                return clientPanel;
            }
        }
        return null;
    }

    void addAllPanels() {
        for (int i = 0; i < clientPanels.size(); i++) {
            ClientPanel clientPanel = (ClientPanel) clientPanels.elementAt(i);
            mainForm.addClientPanel(clientPanel);
        }
    }

    public void setDeafualtPanel(String ipAddress) {
        ClientPanel clientPanel = getClientPanel(ipAddress);
        clientPanel.setFree(true);
        clientPanel.setIpAddress("");
        clientPanel.setHostName("Unknown");
        clientPanel.setCurrentUserNeame("Unknown");
        clientPanel.setOsName("Unknown");
        clientPanel.setOsVersion("Unknown");
        clientPanel.setOsArch("Unknown");
        clientPanel.setDefaultImage();
    }

    public void setSelected(ClientPanel currentClientPanel) {
        if (selectedClientPanel != null) {
            selectedClientPanel.setBorder(new LineBorder(Color.BLACK));
            selectedClientPanel.setSeleced(false);
        }
        currentClientPanel.setBorder(new LineBorder(Color.RED, 2));
        currentClientPanel.setSeleced(true);
        currentClientPanel.updateUI();
        mainForm.setSelectedClientInfo(currentClientPanel.getClientIp(),
                currentClientPanel.getHostName(), currentClientPanel.getOsName(),
                currentClientPanel.getOsVersion(), currentClientPanel.getOsArch(),
                currentClientPanel.getCurrentUserNeame());
        selectedClientPanel = currentClientPanel;
    }
    public ClientPanel getSelectedClientPanel(){
        for (int i = 0; i < clientPanels.size(); i++) {
            ClientPanel clientPanel = (ClientPanel) clientPanels.elementAt(i);
            if(clientPanel.isSeleced()){
                return clientPanel;
            }
        }
        return null;
    }
}
