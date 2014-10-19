/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.conn;

import com.bean.ActionRequestBean;

/**
 *
 * @author Administrator
 */
public class RequestHandler {

    ActionManager actionManager = null;

    public RequestHandler() {
        actionManager = new ActionManager();
    }

    public void processServerRequest(String request) {
        if (request.equals("GET_FULL_SCREEN")) {
            DesktopImageManger.scaledImage = false;
            DesktopImageManger.delay = 0;
            DesktopImageManger.percentage = 0.001;
        } else if (request.trim().equals("STOP_FULL_SCREEN")) {
            DesktopImageManger.scaledImage = false;
            DesktopImageManger.percentage = 1;
            // DesktopImageManger.delay = 5 * 1000;
        }
    }

    public void actionHandler(ActionRequestBean request) {

        if (request.getAction().equals(ActionManager.MOUSE_MOVE)) {
            int x = request.getxCord();
            int y = request.getyCord();
            System.out.println(x + "-" + y);
            actionManager.moveMousePointer(x, y);
        } else if (request.getAction().equals(ActionManager.MOUSE_PRESSED)) {
            actionManager.pressMouse(request.getMouseButton());
        } else if (request.getAction().equals(ActionManager.MOUSE_RELEASED)) {
            actionManager.mouseRelease(request.getMouseButton());
        }else if(request.getAction().equals(ActionManager.KEY_PRESSED)){
            System.out.println("Key Pressed - "+(char)request.getKeyCode());
            actionManager.perssKey(request.getKeyCode());
        }else if(request.getAction().equals(ActionManager.KEY_RELEASED)){
            System.out.println("Key Released - "+(char)request.getKeyCode());
            actionManager.releaseKey(request.getKeyCode());
        }
    }
}
