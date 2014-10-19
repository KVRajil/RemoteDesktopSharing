/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.gui.mngr;

import com.server.gui.*;
import com.server.gui.ImagePanel;
import com.server.utils.ImageHandler;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author ADMIN
 */
public class SwingWorkerForImage extends SwingWorker {

    File[] imageFiles = null;
    ImageHandler imageHandler = null;
    JPanel imageContainer = null;
    private boolean running = true;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public SwingWorkerForImage() {
        imageHandler = new ImageHandler();
    }

    public void setImageContainer(JPanel imageContainer) {
        this.imageContainer = imageContainer;
    }

    public void setImageFiles(File[] imageFiles) {
        this.imageFiles = imageFiles;
    }

    @Override
    protected void done() {
        super.done();
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                imageContainer.updateUI();
            }
        });
    }

    @Override
    protected Object doInBackground() throws Exception {
        if (imageFiles != null) {
            imageContainer.removeAll();
            for (int i = 0; i < imageFiles.length; i++) {
                if (!isRunning()) {
                    break;
                }
                ImageIcon imageIcon = new ImageIcon(imageFiles[i].getAbsolutePath());
                Image image = imageIcon.getImage();
                int imgWidth = image.getWidth(null);
                int imgHeight = image.getHeight(null);
                int labelWidth = 148;
                int labelHeight = 83;
                imageIcon = imageHandler.getScaledImage(image,
                        imgWidth, imgHeight, labelWidth, labelHeight);
                ImagePanel imagePanel = new ImagePanel();
                imagePanel.setImageForPane(imageIcon);
                imagePanel.setFileName(imageFiles[i].getAbsolutePath());
                imagePanel.setIMageNumber(i);
                imageContainer.add(imagePanel);
                imageContainer.updateUI();
            }
        }
        return null;
    }

    public void start() {
        super.execute();
    }
}
