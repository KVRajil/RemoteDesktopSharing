/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.gui.mngr;

import com.server.gui.*;
import com.server.conn.ConnectionManager;
import com.server.utils.SettingsConstatnts;
import com.server.utils.Utility;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Utilities;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Administrator
 */
public class NavigationManager {

    ServerMainForm mainForm = null;
    ImagePanelManager imagePanelManager = null;
    SwingWorkerForImage prvSwingWorker = null;
    Utility utility = new Utility();
    public NavigationManager(ServerMainForm mainForm) {
        this.mainForm = mainForm;
        imagePanelManager = new ImagePanelManager(mainForm);

    }

    public void navigate(String option, int level, DefaultMutableTreeNode selectedNode) {

        System.out.println(level);
        if (prvSwingWorker != null) {
            prvSwingWorker.setRunning(false);
        }
        if (option.trim().equals("View History")) {
            mainForm.removeAllPanel();
            utility.addTreeNodeForHistory(selectedNode);
        } else if (option.trim().equals("Remote Monitor")) {
            mainForm.removeAllPanel();
            if (ConnectionManager.panelManager != null && ConnectionManager.serverStarted) {
                mainForm.setImageViewLayout();
                ConnectionManager.panelManager.addAllPanels();
            } else {
                mainForm.setPannelLabel();
            }
        } else if (level == 3) {
            try {
                mainForm.removeAllPanel();
                DefaultMutableTreeNode ipNode =
                        ((DefaultMutableTreeNode) selectedNode.getParent());
                DefaultMutableTreeNode typeNode =
                        (DefaultMutableTreeNode) ipNode.getParent();
                String ipAddress = ipNode.toString();
                String type = typeNode.toString();
                System.out.println(ipAddress);
                String date = selectedNode.toString();
                File[] imageFiles = getFileNames(ipAddress + File.separator + date, type);
                if (prvSwingWorker != null) {
                    prvSwingWorker.setRunning(false);
                }
                SwingWorkerForImage workerForTree = new SwingWorkerForImage();
                mainForm.setImageViewLayout();
                workerForTree.setImageContainer(mainForm.getPanel());
                workerForTree.setImageFiles(imageFiles);
                workerForTree.start();
                prvSwingWorker = workerForTree;
            } catch (Exception ex) {
                Logger.getLogger(NavigationManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            mainForm.removeAllPanel();
            mainForm.setWelcomePage();
        }
    }

    private File[] getFileNames(String path, String type) {
        String dirName = "";
        if (type.equals("View History")) {
            dirName = System.getProperty("user.dir") + File.separator
                    + SettingsConstatnts.IMG_DIR + File.separator + path;
        } else {
            dirName = System.getProperty("user.dir") + File.separator
                    + SettingsConstatnts.BACKUP_DIR + File.separator + path;
        }
        File imagDir = new File(dirName);
        if (imagDir.exists()) {
            return imagDir.listFiles();
        } else {
            return null;
        }
    }
}
