package com.server.voicechat;

import com.server.gui.ChatWithClientJFrame;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;

import javax.sound.sampled.TargetDataLine;

public class VoiceChat {

    public static boolean stopCapture = true;
    private ByteArrayOutputStream byteArrayOutputStream = null;
    private AudioFormat audioFormat = null;
    private TargetDataLine targetDataLine = null;
    private Port lineIn = null;
    private AudioInputStream audioInputStream = null;
    private BufferedOutputStream out = null;
    private BufferedInputStream in = null;
    private Socket sock = null;
    private SourceDataLine sourceDataLine = null;
    private String ipAddress = "";
    ChatWithClientJFrame voiceChatFrame = null;
    public VoiceChat(String ipAddress, ChatWithClientJFrame voiceChatFrame) {
        this.ipAddress = ipAddress;
        this.voiceChatFrame = voiceChatFrame;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public boolean isChating() {
        return (!stopCapture);
    }

    public void startCapture() {
        try {
            sock = new Socket(ipAddress, 50000);
            out = new BufferedOutputStream(sock.getOutputStream());
            in = new BufferedInputStream(sock.getInputStream());

            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
            System.out.println("Available mixers:");
            for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
                System.out.println(mixerInfo[cnt].getName());
            }
            audioFormat = getAudioFormat();

            DataLine.Info dataLineInfo = new DataLine.Info(
                    TargetDataLine.class, audioFormat);
            for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
                try {
                    Mixer mixer = AudioSystem.getMixer(mixerInfo[cnt]);
                    targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
                    System.err.println(mixerInfo[cnt].getName());
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
 
            if (targetDataLine == null) {
            }
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            Thread captureThread = new CaptureThread();
            captureThread.setName("Capture Thread Tx");
            captureThread.start();

            DataLine.Info dataLineInfo1 = new DataLine.Info(
                    SourceDataLine.class, audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo1);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            Thread playThread = new PlayThread();
            playThread.setName("Play Thread TX");
            playThread.start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void stopCapture() {
        stopCapture = true;
    }

    class CaptureThread extends Thread {

        byte tempBuffer[] = new byte[10000];

        @Override
        public void run() {
            byteArrayOutputStream = new ByteArrayOutputStream();
            stopCapture = false;
            try {
                while (!stopCapture) {
                    int cnt = targetDataLine.read(tempBuffer, 0,
                            tempBuffer.length);
                    out.write(tempBuffer);
                    if (cnt > 0) {
                        byteArrayOutputStream.write(tempBuffer, 0, cnt);
                    }
                }
                byteArrayOutputStream.close();
                out.close();                
            } catch (Exception e) {
                System.out.println(e);
            }
            targetDataLine.close();            
            stopCapture = true;
            voiceChatFrame.stopTalk();
        }
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 8000.0F;

        int sampleSizeInBits = 16;

        int channels = 1;

        boolean signed = true;

        boolean bigEndian = false;

        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
                bigEndian);
    }

//    public static void changeVolum(int value) {
//        FloatControl volCtrl = (FloatControl) lineIn.getControl(
//                FloatControl.Type.VOLUME);
//        volCtrl.setValue(value);
//    }
    class PlayThread extends Thread {

        byte tempBuffer[] = new byte[10000];

        @Override
        public void run() {
            try {
                while (in.read(tempBuffer) != -1 && !VoiceChat.stopCapture) {
                    sourceDataLine.write(tempBuffer, 0, 10000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sourceDataLine.close();
            stopCapture = true;
            voiceChatFrame.stopTalk();
        }
    }
}
