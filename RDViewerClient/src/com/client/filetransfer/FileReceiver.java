package com.client.filetransfer;



import java.io.File;
import java.io.InputStream;
import java.net.Socket;
import java.net.ServerSocket;
import javax.swing.JOptionPane;

public class FileReceiver implements Runnable {

    private Socket socket;
    private ServerSocket listener;    
    public static String destinationPath = System.getProperty("user.dir");
    public FileReceiver(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        while (true) {
            File file = null;
            try {               
                InputStream in = socket.getInputStream();
                int nof_files = ByteStream.toInt(in);
                for (int cur_file = 0; cur_file <
                        nof_files; cur_file++) {
                    String file_name = ByteStream.toString(in);
                    System.out.println(file_name);
                    JOptionPane.showMessageDialog(null, "You have received file : "+ file_name+ ",  from admin ", "File Received ", JOptionPane.INFORMATION_MESSAGE);
                    file = new File(destinationPath,file_name);
                    ByteStream.toFile(in, file);
                }               
            } catch (java.lang.Exception ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
}
