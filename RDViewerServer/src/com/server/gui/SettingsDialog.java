/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SettingsDialog.java
 *
 * Created on Oct 28, 2009, 4:04:12 PM
 */
package com.server.gui;

import com.server.conn.ClientManager;
import com.server.conn.ConnectionManager;
import com.server.utils.SettingsConstatnts;
import java.util.Enumeration;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class SettingsDialog extends javax.swing.JDialog {

    /** Creates new form SettingsDialog */
    ServerMainForm mainForm = null;

    public SettingsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setCurrentSettings();
        mainForm = (ServerMainForm) parent;
        okButton.setEnabled(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        saveImgCheckBox = new javax.swing.JCheckBox();
        timeComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        sendImageCheckBox = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        mailIdTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        mailServerTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        mailServerPswdPasswordField = new javax.swing.JPasswordField();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Settings");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Save Image Settings"));

        saveImgCheckBox.setText("Save Screenshots");
        saveImgCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                saveImgCheckBoxItemStateChanged(evt);
            }
        });
        saveImgCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImgCheckBoxActionPerformed(evt);
            }
        });

        timeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "2", "5", "10", "20", "30", "60" }));
        timeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                timeComboBoxItemStateChanged(evt);
            }
        });
        timeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setText("min.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(timeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1))
                    .addComponent(saveImgCheckBox))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveImgCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));

        sendImageCheckBox.setText("Send Screenshot to Mail");
        sendImageCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sendImageCheckBoxItemStateChanged(evt);
            }
        });

        jLabel5.setText("E-Mail:");

        jLabel6.setText("Mail Server:");

        jLabel7.setText("Mail Server Password");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendImageCheckBox)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mailIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(mailServerTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(mailServerPswdPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(sendImageCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(mailIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(mailServerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(mailServerPswdPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/server/icons/options.png"))); // NOI18N
        jLabel8.setText("jLabel8");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(216, Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // TODO add your handling code here:
        boolean error = false;


        if (sendImageCheckBox.isSelected()) {
            if (!mailIdTextField.getText().equals("")
                    && !mailServerTextField.getText().equals("")
                    && !(mailServerPswdPasswordField.getPassword().length == 0)) {
                SettingsConstatnts.SEND_MAIL = sendImageCheckBox.isSelected();
                SettingsConstatnts.MAIL_SEND_TO = mailIdTextField.getText();
                SettingsConstatnts.MAIL_SERVER = mailServerTextField.getText();
                SettingsConstatnts.MAIL_SERVER_PSWD =
                        new String(mailServerPswdPasswordField.getPassword());
                error = false;
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please provide mail sending informations", "Invalid Fields", WIDTH);
                error = true;
            }

        }
        if (!error) {
            SettingsConstatnts.SEND_MAIL = sendImageCheckBox.isSelected();
            SettingsConstatnts.SAVE_IMAGE = saveImgCheckBox.isSelected();

            this.dispose();
        }
        SettingsConstatnts.CAPTURING_INRVL = Integer.parseInt(timeComboBox.getSelectedItem().toString());
        System.out.println("Capturing intervel : " + Integer.parseInt(timeComboBox.getSelectedItem().toString()));
        if (!ConnectionManager.connectedClients.isEmpty()) {
            Enumeration keys = ConnectionManager.connectedClients.keys();
            while (keys.hasMoreElements()) {
                Object nextElement = keys.nextElement();
                if (nextElement instanceof String) {
                    System.out.println("nxt ellement" + nextElement);
                    ClientManager client = (ClientManager) ConnectionManager.connectedClients.get(nextElement);
                    client.sendRequestToClient("TIME:" + SettingsConstatnts.CAPTURING_INRVL);
                } else {
                    System.out.println(nextElement.getClass().getName());
                }
            }
        }

    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void sendImageCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sendImageCheckBoxItemStateChanged
        // TODO add your handling code here:
        enableSendImageComp(sendImageCheckBox.isSelected());
        okButton.setEnabled(true);
    }//GEN-LAST:event_sendImageCheckBoxItemStateChanged

    private void saveImgCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveImgCheckBoxActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_saveImgCheckBoxActionPerformed

    private void saveImgCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_saveImgCheckBoxItemStateChanged
        // TODO add your handling code here:
        enableSaveImageComp(saveImgCheckBox.isSelected());
        okButton.setEnabled(true);
}//GEN-LAST:event_saveImgCheckBoxItemStateChanged

private void timeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeComboBoxActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_timeComboBoxActionPerformed

private void timeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_timeComboBoxItemStateChanged
// TODO add your handling code here:
    okButton.setEnabled(true);
}//GEN-LAST:event_timeComboBoxItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                SettingsDialog dialog = new SettingsDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField mailIdTextField;
    private javax.swing.JPasswordField mailServerPswdPasswordField;
    private javax.swing.JTextField mailServerTextField;
    private javax.swing.JButton okButton;
    private javax.swing.JCheckBox saveImgCheckBox;
    private javax.swing.JCheckBox sendImageCheckBox;
    private javax.swing.JComboBox timeComboBox;
    // End of variables declaration//GEN-END:variables

    private void enableSaveImageComp(boolean selected) {
    }

    private void setCurrentSettings() {
        saveImgCheckBox.setSelected(SettingsConstatnts.SAVE_IMAGE);
        enableSaveImageComp(SettingsConstatnts.SAVE_IMAGE);
        if (SettingsConstatnts.SAVE_IMAGE) {
//            imgSizeComboBox.setSelectedItem(SettingsConstatnts.SIZE_IMAGE_PERC);
        }
        sendImageCheckBox.setSelected(SettingsConstatnts.SEND_MAIL);

        enableSendImageComp(SettingsConstatnts.SEND_MAIL);
        mailIdTextField.setText(SettingsConstatnts.MAIL_SEND_TO);
        mailServerTextField.setText(SettingsConstatnts.MAIL_SERVER);
        mailServerPswdPasswordField.setText(SettingsConstatnts.MAIL_SERVER_PSWD);
        if (SettingsConstatnts.CAPTURING_INRVL == 0) {
            timeComboBox.setSelectedIndex(0);
        } else if (SettingsConstatnts.CAPTURING_INRVL == 2) {
            timeComboBox.setSelectedIndex(1);

        } else if (SettingsConstatnts.CAPTURING_INRVL == 5) {
            timeComboBox.setSelectedIndex(2);
        } else if (SettingsConstatnts.CAPTURING_INRVL == 10) {
            timeComboBox.setSelectedIndex(3);
        } else if (SettingsConstatnts.CAPTURING_INRVL == 20) {
            timeComboBox.setSelectedIndex(4);
        } else if (SettingsConstatnts.CAPTURING_INRVL == 30) {
            timeComboBox.setSelectedIndex(5);
        } else if (SettingsConstatnts.CAPTURING_INRVL == 60) {
            timeComboBox.setSelectedIndex(6);
        }
    }

    private void enableSendImageComp(boolean state) {
        mailIdTextField.setEnabled(state);
        mailServerTextField.setEnabled(state);
        mailServerPswdPasswordField.setEnabled(state);

        jLabel5.setEnabled(state);
        jLabel6.setEnabled(state);
        jLabel7.setEnabled(state);
    }
}
