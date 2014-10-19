/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.util;

import com.bean.ClientInformations;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Utility {

    public ClientInformations getSystemInformations() {
        try {
            String systemInfo = "";
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            String hostName = InetAddress.getLocalHost().getHostName();
            String oSName = System.getProperty("os.name");
            String oSVersion = System.getProperty("os.version");
            String oSArch = System.getProperty("os.arch");
            String currentUser = System.getProperty("user.name");
//            systemInfo = ipAddress + "├" + hostName + "├" + oSName + "├" + oSVersion +
//                    "├" + oSArch + "├" + currentUser;
//            return systemInfo;
            ClientInformations informations = new ClientInformations();
            informations.setCurrentUser(currentUser);
            informations.setIpAddress(ipAddress);
            informations.setHostName(hostName);
            informations.setOsName(oSName);
            informations.setOsVersion(oSVersion);
            informations.setOsArch(oSArch);
            return informations;
        } catch (UnknownHostException ex) {
            return null;
        }
    }
}
