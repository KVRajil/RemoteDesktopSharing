/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Administrator
 */
public class Utility {

    String sourceDirName = "";

    public void saveSettings() {
        ObjectOutputStream saveSettings = null;
        try {
            File confDir = new File(System.getProperty("user.dir") + File.separator + "conf");
            if (!confDir.exists()) {
                confDir.mkdir();
            }
            saveSettings = new ObjectOutputStream(
                    new FileOutputStream(confDir.getAbsolutePath()
                    + File.separator + "Config.conf"));

            SaveSettingsSer saveSettingsSer = new SaveSettingsSer();
            saveSettingsSer.setSaveImage(SettingsConstatnts.SAVE_IMAGE);
            saveSettingsSer.setSizeImagePerc(SettingsConstatnts.SIZE_IMAGE_PERC);
            saveSettingsSer.setCapturingInrvl(SettingsConstatnts.CAPTURING_INRVL);
            saveSettingsSer.setSendMail(SettingsConstatnts.SEND_MAIL);
            saveSettingsSer.setMailSendTo(SettingsConstatnts.MAIL_SEND_TO);
            saveSettingsSer.setMailServer(SettingsConstatnts.MAIL_SERVER);
            saveSettingsSer.setMailServerPswd(SettingsConstatnts.MAIL_SERVER_PSWD);
            saveSettingsSer.setMessages(SettingsConstatnts.messages);
            saveSettings.writeObject(saveSettingsSer);
            saveSettings.flush();
        } catch (IOException ex) {
        } finally {
            try {
                saveSettings.close();
            } catch (IOException ex) {
            }
        }

    }

    public void loadSettings() throws IOException, ClassNotFoundException {
        File settingsFile = new File(System.getProperty("user.dir")
                + File.separator + "conf" + File.separator + "Config.conf");
        if (settingsFile.exists()) {
            ObjectInputStream readSettings = new ObjectInputStream(
                    new FileInputStream(settingsFile));
            SaveSettingsSer saveSettingsSer =
                    (SaveSettingsSer) readSettings.readObject();
            SettingsConstatnts.SAVE_IMAGE = saveSettingsSer.isSaveImage();
            SettingsConstatnts.SIZE_IMAGE_PERC = saveSettingsSer.getSizeImagePerc();
            SettingsConstatnts.CAPTURING_INRVL = saveSettingsSer.getCapturingInrvl();
            SettingsConstatnts.SEND_MAIL = saveSettingsSer.isSendMail();
            SettingsConstatnts.MAIL_SEND_TO = saveSettingsSer.getMailSendTo();
            SettingsConstatnts.MAIL_SERVER = saveSettingsSer.getMailServer();
            SettingsConstatnts.MAIL_SERVER_PSWD = saveSettingsSer.getMailServerPswd();
            SettingsConstatnts.messages = saveSettingsSer.getMessages();
        }
    }

    public String getCurrentDate(String formate) {
        Calendar calendar = Calendar.getInstance();
        if (formate.equalsIgnoreCase("dd-mm-yyyy")) {
            return (calendar.get(Calendar.DAY_OF_MONTH)) + "-"
                    + (calendar.get(Calendar.MONTH) + 1) + "-"
                    + (calendar.get(Calendar.YEAR));
        } else {
            return "";
        }
    }

    public String getTime() {
        return "" + (new Date().getTime());
    }

    public void addTreeNodeForHistory(DefaultMutableTreeNode history) {
        history.removeAllChildren();
        File imageDir = new File(System.getProperty("user.dir") + File.separator
                + "Desktop Images");
        if (imageDir.exists()) {
            File[] clientDirs = imageDir.listFiles();
            for (int i = 0; i < clientDirs.length; i++) {
                String clientIp = clientDirs[i].getName();
                File[] dateDir = clientDirs[i].listFiles();
                DefaultMutableTreeNode clientNode =
                        new DefaultMutableTreeNode(clientIp);
                history.add(clientNode);
                for (int j = 0; j < dateDir.length; j++) {
                    String date = dateDir[j].getName();
                    if (dateDir[j].list().length > 0) {
                        DefaultMutableTreeNode dateNode =
                                new DefaultMutableTreeNode(date);
                        clientNode.add(dateNode);
                    }
                }
            }
        }
    }

    public void addTreeNodeForBackup(DefaultMutableTreeNode backup) {
        File imageDir = new File(System.getProperty("user.dir") + File.separator
                + SettingsConstatnts.BACKUP_DIR);
        if (imageDir.exists()) {
            File[] clientDirs = imageDir.listFiles();
            for (int i = 0; i < clientDirs.length; i++) {
                String clientIp = clientDirs[i].getName();
                DefaultMutableTreeNode clientNode =
                        new DefaultMutableTreeNode(clientIp);
                backup.add(clientNode);
                File[] dateDir = clientDirs[i].listFiles();
                for (int j = 0; j < dateDir.length; j++) {
                    String date = dateDir[j].getName();
                    if (dateDir[j].list().length > 0) {
                        DefaultMutableTreeNode dateNode =
                                new DefaultMutableTreeNode(date);
                        clientNode.add(dateNode);
                    }
                }
            }
        }
    }

    public String getDate(Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(time);
    }

    public String getTime(Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        return dateFormat.format(time);
    }

    public void getDirName(String string, String systemDirName) {
    }

    public String getImageDir(DefaultMutableTreeNode selectedNode) {
        if (selectedNode.getLevel() == 2) {
            String ipAddress = (String) selectedNode.getUserObject();
            DefaultMutableTreeNode parant = (DefaultMutableTreeNode) selectedNode.getParent();
            if (((String) parant.getUserObject()).equals("View History")) {
                return System.getProperty("user.dir") + File.separator
                        + SettingsConstatnts.IMG_DIR + File.separator + ipAddress;
            } else {
                return System.getProperty("user.dir") + File.separator
                        + SettingsConstatnts.BACKUP_DIR + File.separator + ipAddress;
            }
        } else {
            String date = (String) selectedNode.getUserObject();
            DefaultMutableTreeNode parantIp = (DefaultMutableTreeNode) selectedNode.getParent();
            String ipAddress = (String) parantIp.getUserObject();
            DefaultMutableTreeNode parant = (DefaultMutableTreeNode) parantIp.getParent();
            if (((String) parant.getUserObject()).equals("View History")) {
                return System.getProperty("user.dir") + File.separator
                        + SettingsConstatnts.IMG_DIR + File.separator + ipAddress
                        + File.separator + date;
            } else {
                return System.getProperty("user.dir") + File.separator
                        + SettingsConstatnts.BACKUP_DIR + File.separator + ipAddress
                        + File.separator + date;
            }
        }
    }

    public File zip(boolean firstTime, File source, ZipOutputStream zipOut)
            throws IOException {

        if (firstTime) {
            if (!source.exists()) {
                return null;
            }
            sourceDirName = source.getName();
        }
        if (source.isDirectory()) {
            File[] entries = source.listFiles();
            if (entries.length == 0) {
                // System.out.println(getRelativePath(source.getPath(), sourceDirName) + "\\");
                //  ZipEntry entry = new ZipEntry(getRelativePath(source.getPath(),
                //        sourceDirName) + "/");
                //  zipOut.putNextEntry(entry);
                source.delete();
            } else {
                for (int i = 0; i < entries.length; i++) {
                    zip(false, entries[i], zipOut);
                }
            }
        } else {
            String relativePath = getRelativePath(source.getPath(), sourceDirName);
            System.out.println(relativePath);
            ZipEntry entry = new ZipEntry(relativePath);
            zipOut.putNextEntry(entry);
            FileInputStream fileIS = new FileInputStream(source);
            byte[] readData = new byte[1024];
            int readBytes = -1;
            while ((readBytes = fileIS.read(readData)) != -1) {
                zipOut.write(readData, 0, readBytes);
            }
            zipOut.flush();
            fileIS.close();
            source.deleteOnExit();
        }
        return null;
    }

    private String getRelativePath(String path, String sourceFileName) {
        return path.substring(path.indexOf(sourceFileName));
    }

    public void clearHistory(File dir) {
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] subDirs = dir.listFiles();
            for (int i = 0; subDirs != null && i < subDirs.length; i++) {
                clearHistory(subDirs[i]);
            }
            dir.delete();
        } else if (dir != null && dir.exists() && dir.isFile()) {
            dir.delete();
        }
    }
}
