/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.voicechat;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author SAFI
 */
public class Audio {

    SourceDataLine line = null;

    private void playAlarm(String fileName) {

        int EXTERNAL_BUFFER_SIZE = 128000;
        File soundFile = new File(fileName);
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e) {

            e.printStackTrace();
            System.exit(1);
        }
        javax.sound.sampled.AudioFormat audioFormat = audioInputStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                audioFormat);
        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        line.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                int nBytesWritten = line.write(abData, 0, nBytesRead);
            }
        }
        line.drain();
        line.close();
    }

    public void stop() {
        if (line != null && line.isRunning()) {
            line.stop();
        }
    }
    public static void main(String[] args) {
       Audio audio = new Audio();
       while(true){
            try {
                audio.playAlarm("ringout.wav");
                Thread.sleep(100);
                audio.playAlarm("ringout.wav");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }
}
