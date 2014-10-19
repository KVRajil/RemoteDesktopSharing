/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.gui.mngr;

import com.server.gui.*;
import com.server.utils.ImageHandler;
import java.awt.Image;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class ImagePanelManager {

    ServerMainForm mainForm = null;
    ImageHandler imageHandler = null;
    JPanel panel = null;

    public ImagePanelManager(ServerMainForm mainForm) {
        this.mainForm = mainForm;
        imageHandler = new ImageHandler();
    }

    void addImagePanels(File[] imageFiles) {
        if (imageFiles != null) {
            try {
//            for (int i = 0; i < imageFiles.length; i++) {
//                ImageIcon imageIcon = new ImageIcon(imageFiles[i].getAbsolutePath());
//                Image image = imageIcon.getImage();
//                int imgWidth = image.getWidth(null);
//                int imgHeight = image.getHeight(null);
//                int labelWidth = 148;
//                int labelHeight = 83;
//                imageIcon = imageHandler.getScaledImage(image,
//                        imgWidth, imgHeight, labelWidth, labelHeight);
//                ImagePanel imagePanel = new ImagePanel();
//                imagePanel.setImageForPane(imageIcon);
                panel = mainForm.getPanel();
                
            } catch (Exception ex) {
                Logger.getLogger(ImagePanelManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
