package com.client.voicechat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class VoiceSender implements Runnable {

    private Socket clientSocket = null;
    private BufferedInputStream input = null;
    static TargetDataLine targetDataLine = null;
    private BufferedOutputStream out = null;
    private ByteArrayOutputStream byteArrayOutputStream = null;
    private AudioFormat audioFormat = null;
    private SourceDataLine sourceDataLine = null;
    private byte tempBuffer[] = new byte[10000];
    public static boolean stopCapture = false;
    private ListenForVoiceChat forVoiceChat = null;

    public VoiceSender(Socket clientSocket, ListenForVoiceChat forVoiceChat) {
        this.clientSocket = clientSocket;
        this.forVoiceChat = forVoiceChat;
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 8000.0F;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(
                sampleRate,
                sampleSizeInBits,
                channels,
                signed,
                bigEndian);
    }

//    public static void main(String s[]) throws LineUnavailableException {
//        VoiceSender s2 = new VoiceSender();
//    }
    private void captureAudio() {
        try {

            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
            System.out.println("Available mixers:");

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
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            Thread captureThread = new CaptureThread();
            captureThread.setName("Capture - Voice Sender ");
            captureThread.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    class CaptureThread extends Thread {

        byte tempBuffer[] = new byte[10000];

        @Override
        public void run() {
            stopCapture = false;
            try {
                while (!stopCapture) {
                    int cnt = targetDataLine.read(tempBuffer, 0, tempBuffer.length);
                    out.write(tempBuffer);
                }
            } catch (Exception e) {
                System.out.println(e);
                stopCapture = true;
            }
            stopChating();
            targetDataLine.close();
            stopCapture = true;
        }
    }

    public static void changeVolum(int value) {
        FloatControl volCtrl = (FloatControl) targetDataLine.getControl(
                FloatControl.Type.VOLUME);
        volCtrl.setValue(value);
    }

    public void run() {
        try {
            stopCapture = false;
            audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();
            captureAudio();
            input = new BufferedInputStream(clientSocket.getInputStream());
            out = new BufferedOutputStream(clientSocket.getOutputStream());
            while (input.read(tempBuffer) != -1 && !stopCapture) {
                sourceDataLine.write(tempBuffer, 0, 10000);
            }
        } catch (LineUnavailableException ex) {
            Logger.getLogger(VoiceSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopChating();
    }

    public void stopChating() {
        try {
            stopCapture = true;
            input.close();
            out.close();
            sourceDataLine.close();
            forVoiceChat.closeChat();
        } catch (IOException ex) {
            Logger.getLogger(VoiceSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
