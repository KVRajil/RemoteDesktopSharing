/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.conn;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ActionManager {

    public static final String MOUSE_MOVE = "MOUSE_MOVE";
    public static final String MOUSE_PRESSED = "MOUSE_PRESSED";
    public static final String MOUSE_RELEASED = "MOUSE_RELEASED";
    public static final String KEY_PRESSED = "KEY_PRESSED";
    public static final String KEY_RELEASED = "KEY_RELEASED";
    Robot performAction = null;

    public ActionManager() {
        try {
            performAction = new Robot();

        } catch (AWTException ex) {
            Logger.getLogger(ActionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void moveMousePointer(int x, int y) {
        performAction.mouseMove(x, y);
    }

    public void pressMouse(int mouseButton) {
        if (mouseButton == MouseEvent.BUTTON1) {
            performAction.mousePress(InputEvent.BUTTON1_MASK);
        } else if (mouseButton == MouseEvent.BUTTON2) {
            performAction.mousePress(InputEvent.BUTTON2_MASK);
        } else if (mouseButton == MouseEvent.BUTTON3) {
            performAction.mousePress(InputEvent.BUTTON3_MASK);
        }
    }

    public void mouseRelease(int mouseButton) {
        if (mouseButton == MouseEvent.BUTTON1) {
            performAction.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (mouseButton == MouseEvent.BUTTON2) {
            performAction.mouseRelease(InputEvent.BUTTON2_MASK);
        } else if (mouseButton == MouseEvent.BUTTON3) {
            performAction.mouseRelease(InputEvent.BUTTON3_MASK);
        }
    }

    void perssKey(int keyCode) {
        performAction.keyPress(keyCode);
    }

    void releaseKey(int keyCode) {
        performAction.keyRelease(keyCode);
    }
}
