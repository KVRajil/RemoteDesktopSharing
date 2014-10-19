/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.utils;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class SaveSettingsSer implements Serializable {

    private boolean saveImage = false;
    private int sizeImagePerc = 50;
    private boolean sendMail = false;
    private String mailSendTo = "jexena.coet1@gmail.com";
    private String mailServer = "jexena.coet@gmail.com";
    private String mailServerPswd = "jexenacoet";
    private int capturingInrvl = 0;
    private boolean deleteImgsAfterSend = false;
    private Vector<MessageInfo> messages = new Vector<MessageInfo>();

    /**
     * @return the saveImage
     */
    public boolean isSaveImage() {
        return saveImage;
    }

    /**
     * @param saveImage the saveImage to set
     */
    public void setSaveImage(boolean saveImage) {
        this.saveImage = saveImage;
    }

    /**
     * @return the sizeImagePerc
     */
    public int getSizeImagePerc() {
        return sizeImagePerc;
    }

    /**
     * @param sizeImagePerc the sizeImagePerc to set
     */
    public void setSizeImagePerc(int sizeImagePerc) {
        this.sizeImagePerc = sizeImagePerc;
    }

    /**
     * @return the mailSendTo
     */
    public String getMailSendTo() {
        return mailSendTo;
    }

    /**
     * @param mailSendTo the mailSendTo to set
     */
    public void setMailSendTo(String mailSendTo) {
        this.mailSendTo = mailSendTo;
    }

    /**
     * @return the mailServer
     */
    public String getMailServer() {
        return mailServer;
    }

    /**
     * @param mailServer the mailServer to set
     */
    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;
    }

    /**
     * @return the mailServerPswd
     */
    public String getMailServerPswd() {
        return mailServerPswd;
    }

    /**
     * @param mailServerPswd the mailServerPswd to set
     */
    public void setMailServerPswd(String mailServerPswd) {
        this.mailServerPswd = mailServerPswd;
    }

    /**
     * @return the capturingInrvl
     */
    public int getCapturingInrvl() {
        return capturingInrvl;
    }

    /**
     * @param capturingInrvl the capturingInrvl to set
     */
    public void setCapturingInrvl(int capturingInrvl) {
        this.capturingInrvl = capturingInrvl;
    }

    /**
     * @return the sendMail
     */
    public boolean isSendMail() {
        return sendMail;
    }

    /**
     * @param sendMail the sendMail to set
     */
    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    /**
     * @return the deleteImgsAfterSend
     */
    public boolean isDeleteImgsAfterSend() {
        return deleteImgsAfterSend;
    }

    /**
     * @param deleteImgsAfterSend the deleteImgsAfterSend to set
     */
    public void setDeleteImgsAfterSend(boolean deleteImgsAfterSend) {
        this.deleteImgsAfterSend = deleteImgsAfterSend;
    }

    public Vector<MessageInfo> getMessages() {
        return messages;
    }

    public void setMessages(Vector<MessageInfo> messages) {
        this.messages = messages;
    }
}
