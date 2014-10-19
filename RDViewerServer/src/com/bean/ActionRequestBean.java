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
public class ActionRequestBean implements Serializable {

    private String action = "";
    private int xCord = 0;
    private int yCord = 0;
    private int clickCount = 0;
    private int mouseButton = 0;
    private int keyCode = 0;

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the xCord
     */
    public int getxCord() {
        return xCord;
    }

    /**
     * @param xCord the xCord to set
     */
    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    /**
     * @return the yCord
     */
    public int getyCord() {
        return yCord;
    }

    /**
     * @param yCord the yCord to set
     */
    public void setyCord(int yCord) {
        this.yCord = yCord;
    }

    /**
     * @return the keyCode
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * @param keyCode the keyCode to set
     */
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * @return the clickCount
     */
    public int getClickCount() {
        return clickCount;
    }

    /**
     * @param clickCount the clickCount to set
     */
    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    /**
     * @return the mouseButton
     */
    public int getMouseButton() {
        return mouseButton;
    }

    /**
     * @param mouseButton the mouseButton to set
     */
    public void setMouseButton(int mouseButton) {
        this.mouseButton = mouseButton;
    }
}
