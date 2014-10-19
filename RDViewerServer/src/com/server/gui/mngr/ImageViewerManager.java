/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.gui.mngr;

import com.server.gui.ImageViewerDialog;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class ImageViewerManager {

    private ImageViewerDialog imageViewer = null;
    private File[] imageFiles = null;
    public ImageViewerManager(ImageViewerDialog imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void nextImage() {

    }
}
