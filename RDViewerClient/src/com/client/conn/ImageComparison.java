/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.conn;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Administrator
 */
public class ImageComparison {

    public boolean compareImge(Image currentImage, Image nextImage, double percLimit) {
        if (currentImage != null && nextImage != null) {
            try {
                PixelGrabber currentPg = new PixelGrabber(currentImage, 0, 0,
                        currentImage.getWidth(null), currentImage.getHeight(null), false);
                PixelGrabber nextPg = new PixelGrabber(nextImage, 0, 0,
                        nextImage.getWidth(null), nextImage.getHeight(null), false);
                int[] currImgData = null;
                int[] nextImgData = null;
                if (currentPg.grabPixels()) {
                    int width = currentPg.getWidth();
                    int height = currentPg.getHeight();
                    currImgData = new int[width * height];
                    currImgData = (int[]) currentPg.getPixels();
                }
                if (nextPg.grabPixels()) {
                    int width = nextPg.getWidth();
                    int height = nextPg.getHeight();
                    nextImgData = new int[width * height];
                    nextImgData = (int[]) nextPg.getPixels();
                }
                int tolerance = 0;
                for (int i = 0; i < currImgData.length && i < nextImgData.length; i++) {
                    if (currImgData[i] == nextImgData[i]) {
                        //  System.out.println(currImgData[i] + " : " + nextImgData[i]);
                    } else {
                        // System.err.println(currImgData[i] + " : " + nextImgData[i]);
                        tolerance++;
                    }
                }
                //System.out.println("*********************" + tolerance + "*****************");
                double perce = ((double)tolerance / (double)currImgData.length)*100;
             //   System.out.println(perce);
                // System.out.println(new MyDataComparator().compare(currImgData, nextImgData));
                //return Arrays.equals(currImgData, nextImgData);
                if(perce > percLimit){
                    return false;
                }else{
                    return true;
                }
            } catch (InterruptedException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    void saveImage(BufferedImage nextImage, String fileName) {
        try {
            File outputFile = new File(fileName+".jpg");
            ImageIO.write(nextImage, "jpeg", outputFile);
        } catch (IOException ex) {
            Logger.getLogger(ImageComparison.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
