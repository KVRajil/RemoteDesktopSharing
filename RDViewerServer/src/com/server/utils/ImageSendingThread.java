/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Administrator
 */
public class ImageSendingThread extends TimerTask {

    public static boolean RUNNING = true;
    private int imageCount = 0;
    String sourceDirName = "";
    ZipOutputStream zipOut = null;

    public void run() {
        try {
            if (SettingsConstatnts.SEND_MAIL) {
                File sendingFile = getSendingFile();
  
            }
        } catch (IOException ex) {
            Logger.getLogger(ImageSendingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private File getSendingFile() throws IOException {
        File imgDir = new File(SettingsConstatnts.IMG_DIR);
        if (imgDir.exists() && imgDir.isDirectory()) {

            File tempDir = new File(SettingsConstatnts.TEMP_DIR);
            if (!tempDir.exists()) {
                tempDir.mkdir();
            }
            File compressedFile = zip(true, imgDir, tempDir, imgDir.getName());

            // zipOut.close();
            return compressedFile;

        }
        return null;
    }

    private File zip(boolean firstTime, File source, File destDir, String destFileName)
            throws IOException {
        System.out.println("source file : " + source);
        System.out.println("dest file : " + destDir);
        System.out.println("dest file  (String): " + destFileName);
        File[] subDirs = source.listFiles();
        if (subDirs != null) {
            for (File pcs : subDirs) {
                System.out.println("Folder : " + pcs);
                File[] dateFolders = pcs.listFiles();
                if (dateFolders != null) {
                    for (File dateDir : dateFolders) {
                        System.out.println("date dirs : " + dateDir);
                        File[] imgs = dateDir.listFiles();
                        if (imgs != null) {
                            zipFile(imgs, destDir, "" + dateDir);
                        }
                    }
                }
            }
        }


        return null;
    }

    private String getRelativePath(String path, String sourceFileName) {
        return path.substring(path.indexOf(sourceFileName));
    }

    public static void main(String[] args) {
        try {
            new ImageSendingThread().getSendingFile();
        } catch (IOException ex) {
            Logger.getLogger(ImageSendingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void moveFile(File source, String destFileName) {
        System.out.println("dest file : "+ destFileName);
        if (source.exists()) {
            try {
                String destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\"));
                System.out.println("dest dir : " + destDirName);
                File destDir = new File(destDirName);
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                FileInputStream readSourse = new FileInputStream(source);
                FileOutputStream writeCopy = new FileOutputStream(destFileName);
                byte[] readData = new byte[1024 * 10];
                int readBytes = -1;
                while ((readBytes = readSourse.read(readData)) != -1) {
                    writeCopy.write(readData, 0, readBytes);
                }
                writeCopy.close();
                readSourse.close();
                source.delete();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ImageSendingThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ImageSendingThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void zipFile(File[] imgs, File destDir, String fileName) throws FileNotFoundException, IOException {
        if (imgs != null) {
            int counter = 0;
            int fileNameCtr = 0;
            ArrayList<File> files = new ArrayList<File>();
            for (int i = 0; i < imgs.length; i++) {

                if (counter == 4) {
                    fileNameCtr++;
                    createZipFile(files, destDir, fileName + "_" + fileNameCtr + ".zip");

                    counter = 0;
                    files.clear();
                } else {
                    counter++;
                    files.add(imgs[i]);
                }
            }
        }
    }

    private void createZipFile(ArrayList<File> files, File destDir, String fileName) throws FileNotFoundException, IOException {

        System.out.println("dest : " + destDir.getAbsolutePath());
        String zipFile = fileName.replace(File.separator, "_");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File sendinFile = new File(destDir, zipFile);

        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(sendinFile));
        for (int i = 0; i < files.size(); i++) {

            ZipEntry zipEntry = new ZipEntry(files.get(i).getName());
            zipOutputStream.putNextEntry(zipEntry);
            FileInputStream fis = new FileInputStream(files.get(i));
            byte[] readData = new byte[1024 * 60];
            int readBytes = -1;
            while ((readBytes = fis.read(readData)) != -1) {
                zipOutputStream.write(readData, 0, readBytes);
            }
            zipOutputStream.flush();
            fis.close();
            String relativePath = getRelativePath(files.get(i).getPath(), sourceDirName);
            System.err.println("Rel path :" + relativePath);
            relativePath = relativePath = SettingsConstatnts.BACKUP_DIR + File.separator
                    + relativePath.substring(relativePath.indexOf("\\") + 1);
            
            moveFile(new File(files.get(i).getAbsolutePath()), relativePath);

        }
        zipOutputStream.close();
        System.out.println("created   zip file  : " + fileName);
         EMail.sendMail("Back up images", "", sendinFile.getAbsolutePath());



    }
}
/*
if (firstTime) {
sourceDirName = source.getName();
zipOut = new ZipOutputStream(new FileOutputStream(outFile));
}
if (source.isDirectory()) {
File[] entries = source.listFiles();
if (entries.length == 0) {
// System.out.println(getRelativePath(source.getPath(), sourceDirName) + "\\");
//  ZipEntry entry = new ZipEntry(getRelativePath(source.getPath(),
//        sourceDirName) + "/");
//  zipOut.putNextEntry(entry);
// source.delete();
} else {
for (int i = 0; i < entries.length; i++) {
zip(false, entries[i], destDir, destFileName);
}

}
} else {

if (imageCount > 25) {

return outFile;
} else {
imageCount++;
}
String relativePath = getRelativePath(source.getPath(), sourceDirName);
System.err.println(relativePath);
ZipEntry entry = new ZipEntry(relativePath);
zipOut.putNextEntry(entry);
FileInputStream fileIS = new FileInputStream(source);
byte[] readData = new byte[1024 * 60];
int readBytes = -1;
while ((readBytes = fileIS.read(readData)) != -1) {
zipOut.write(readData, 0, readBytes);
}
zipOut.flush();
fileIS.close();
zipOut.closeEntry();
relativePath = SettingsConstatnts.BACKUP_DIR + File.separator
+ relativePath.substring(relativePath.indexOf("\\") + 1);
moveFile(source, relativePath);
}
 */
