/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean;

import java.io.Serializable;

/**
 *
 * @author S A F I
 */
public class MessageBean implements Serializable{
    private String subject = "";
    private String message = "";

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
