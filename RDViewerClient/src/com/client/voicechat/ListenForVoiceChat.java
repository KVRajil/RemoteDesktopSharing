/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.voicechat;

import com.client.gui.ClientMain;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ListenForVoiceChat implements Runnable {

    ServerSocket myService = null;
    private ClientMain clientMain = null;
    private VoiceSender voiceSender = null;

    public ListenForVoiceChat(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void run() {
        try {
            myService = new ServerSocket(50000);
            while (true) {
                listenForNewVoiceChat();
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenForVoiceChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listenForNewVoiceChat() {
        try {
            Socket clientSocket = myService.accept();
            clientMain.chatWithServerJFrame.startChat();
            voiceSender = new VoiceSender(clientSocket, this);
            new Thread(voiceSender).start();
        } catch (IOException ex) {
            Logger.getLogger(ListenForVoiceChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void closeChat() {
        clientMain.chatWithServerJFrame.stopTalk();
        voiceSender = null;
    }

    public void stopTalk() {
        if (voiceSender != null) {
            VoiceSender.stopCapture = true;
        }
    }
}
