/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Administrator
 */
public class ImageHandler {

    private Utility utility = new Utility();
    double newImageWidth = 0;
    double newImageHeight = 0;

    public ImageHandler() {
        utility = new Utility();
    }

    public void saveImage(Image image, String fileName) {
        try {
            ImageIO.write(getBufferedImage(image), "jpg", new File(fileName + ".jpg"));
        } catch (IOException ex) {
            Logger.getLogger(ImageHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BufferedImage getBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        try {
//            // Determine the type of transparency of the new buffered image
//            int transparency = Transparency.OPAQUE;
//            if (hasAlpha) {
//                transparency = Transparency.BITMASK;
//            }
//
//            // Create the buffered image
//            GraphicsDevice gs = ge.getDefaultScreenDevice();
//            GraphicsConfiguration gc = gs.getDefaultConfiguration();
//            bimage = gc.createCompatibleImage(
//                    image.getWidth(null), image.getHeight(null), transparency);
//        } catch (HeadlessException e) {
//            // The system does not have a screen
//        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
//            if (hasAlpha) {
//                type = BufferedImage.TYPE_INT_ARGB;
//            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;

    }

    boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }

    public void saveImage(ImageIcon image, String ipAddress) {
        File imageDir = createImageDir(ipAddress);
        String time = utility.getTime();
        saveImage(image.getImage(), imageDir.getPath() + File.separator + time);
    }

    private File createImageDir(String ipAddress) {
        String date = utility.getCurrentDate("dd-mm-yyyy");
        File saveImageDir = new File(SettingsConstatnts.IMG_DIR + File.separator +
                ipAddress + File.separator + date);
        if (saveImageDir.exists()) {
            return saveImageDir;
        } else {
            saveImageDir.mkdirs();
            return saveImageDir;
        }
    }

    public ImageIcon getScaledImage(Image image, int imgWidth, int imgHeight, int labelWidth, int labelHeight) {
        getScalingFactor(imgWidth, imgHeight, labelWidth, labelHeight);
        if (newImageWidth != 0 && newImageHeight != 0) {
            image = image.getScaledInstance(
                    (int) newImageWidth, (int) newImageHeight, Image.SCALE_SMOOTH);
        }
        return new ImageIcon(image);
    }

    private void getScalingFactor(int imgWidth, int imgHeight, int labelWidth,
            int labelHeight) {
        int selectedSide = 0;
        int difference = 0;

        if (imgWidth > labelWidth || imgHeight > labelHeight) {
            if (imgWidth > imgHeight) {
                selectedSide = imgWidth;
                difference = imgWidth - labelWidth;
                float percentage = ((float) difference / (float) imgWidth) * 100;

                newImageHeight = imgHeight - (imgHeight * (percentage / 100));
                newImageWidth = imgWidth - difference;
            } else {
                selectedSide = imgHeight;
                difference = imgHeight - labelHeight;
                float percentage = ((float) difference / (float) imgHeight) * 100;

                newImageWidth = imgWidth - (imgWidth * (percentage / 100));
                newImageHeight = imgHeight - difference;
            }
        }
    }
}
