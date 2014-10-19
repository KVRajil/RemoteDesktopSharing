/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.conn;

/**
 *
 * @author Anoop
 */
import java.util.Scanner;

public class SecToTime {

    public static void main(String arr[]) {
        int hr;
        int mn;
        int sec;
        System.out.println("Second To Time Converter");
        System.out.println("");
        System.out.println("Enter the time in seconds");
        Scanner scan = new Scanner(System.in);
        long s = scan.nextLong();
        hr = (int) (s / 3600);
        int rem = (int) (s % 3600);
        mn = rem / 60;
        sec = rem % 60;
        String hrStr = (hr < 10 ? "0" : "") + hr;
        String mnStr = (mn < 10 ? "0" : "") + mn;
        String secStr = (sec < 10 ? "0" : "") + sec;
        System.out.println(hrStr + " : " + mnStr + " : " + secStr);
    }
}
