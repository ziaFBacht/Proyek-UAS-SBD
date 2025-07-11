/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.uas_sbd;

import java.sql.*;
import javax.swing.*;
import java.time.*;

/**
 *
 * @author User
 */
public class jualMenu extends javax.swing.JFrame {

    /**
     * Creates new form jualMenu
     */
    KoneksiMysql kon;
    Connection c;
    
    String usern = "user123";
    String ruser;
    String rpass;
    
    boolean editingData = false;
    
    public jualMenu() {
        initComponents();
        
        setLocationRelativeTo(null);
        
        kon = new KoneksiMysql("uas_sbd");
        c = kon.getConnection();
                
        try{
            c.setAutoCommit(false);
            c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (Exception ex) {
           
        }
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // This runs BEFORE the window actually closes
                System.out.println("Window is closing...");
                if (editingData = true){
                    antiDeadlock();
                }
            }
        });
        
    }
    
    public jualMenu(String user, String pass){
        initComponents();
        
        setLocationRelativeTo(null);
        
        kon = new KoneksiMysql("localhost",user,pass,"uas_sbd");
        c = kon.getConnection();
        
        ruser = user;
        rpass = pass;
                
        usern = user;
        setUserButton.setEnabled(false);
        
        try{
            c.setAutoCommit(false);
            c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (Exception ex) {
           
        }
    }          
    
    private boolean requestLock(String username) {
    try {
        // Cek apakah sedang dikunci user lain
        PreparedStatement ps = c.prepareStatement("SELECT is_locked, locked_by FROM lock_status WHERE id = 2");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            boolean isLocked = rs.getBoolean("is_locked");
            String lockedBy = rs.getString("locked_by");

            if (isLocked) {
                // Jika dikunci oleh user yang sama, izinkan
                if (username.equals(lockedBy)) {
                    return true;
                }

                // Jika dikunci oleh user lain, tolak
                JOptionPane.showMessageDialog(this, "Record is edited by another user (" + lockedBy + ")");
                editingData = false;
                return false;
            }
        }

        // Ambil kunci
        PreparedStatement lock = c.prepareStatement(
            "UPDATE lock_status SET is_locked = TRUE, locked_by = ?, locked_at = NOW() WHERE id = 2"
        );
        lock.setString(1, username);
        lock.executeUpdate();
        c.commit();
        
        editingData = true;
        return true;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
        }
    }

    
    private void clearSet(){
        kodeTransaksiText.setText(null);
        tanggalTransaksiText.setText(null);
        kodeBarangText.setText(null);
        jumlahJualText.setText(null);
    }
    
    private void enableSaveRoll(Boolean status){
        saveButton.setEnabled(status);
        cancelButton.setEnabled(status);
        if (status == true){
            statusText.setEnabled(true);
            statusText.setText("* Ada Perubahan! Jangan lupa Save!");
        }else{
            statusText.setEnabled(false);
            statusText.setText("Tidak Ada Perubahan");
        }
    }
    
    private void antiDeadlock(){
        if (editingData = true){
            
            try {
                c.rollback();
            
                String sql = "UPDATE lock_status SET is_locked = FALSE, locked_by = NULL, locked_at = NULL WHERE id = 2";
                PreparedStatement unlock = c.prepareStatement(sql);
                unlock.executeUpdate();
            
                c.commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed Command");
            }
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        kodeTransaksiText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tanggalTransaksiText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        kodeBarangText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jumlahJualText = new javax.swing.JTextField();
        insertButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        statusText = new javax.swing.JLabel();
        setUserButton = new javax.swing.JButton();
        displayButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        kodeTransaksiText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeTransaksiTextActionPerformed(evt);
            }
        });

        jLabel1.setText("Kode Transaksi");

        tanggalTransaksiText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tanggalTransaksiTextActionPerformed(evt);
            }
        });

        jLabel2.setText("Tanggal Transaksi");

        jLabel3.setText("Kode Barang");

        kodeBarangText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeBarangTextActionPerformed(evt);
            }
        });

        jLabel4.setText("Jumlah Jual");

        jumlahJualText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahJualTextActionPerformed(evt);
            }
        });

        insertButton.setText("Insert");
        insertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertButtonActionPerformed(evt);
            }
        });

        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.setEnabled(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        statusText.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        statusText.setForeground(new java.awt.Color(255, 0, 0));
        statusText.setText("Tidak Ada Perubahan");
        statusText.setEnabled(false);

        setUserButton.setText("Set User (debug)");
        setUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setUserButtonActionPerformed(evt);
            }
        });

        displayButton.setText("Display");
        displayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Quick Insert");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(statusText, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jumlahJualText, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(kodeTransaksiText, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(kodeBarangText, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tanggalTransaksiText))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(insertButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(displayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(setUserButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insertButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setUserButton)
                    .addComponent(displayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kodeTransaksiText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tanggalTransaksiText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kodeBarangText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jumlahJualText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(saveButton)
                    .addComponent(resetButton)
                    .addComponent(statusText))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kodeTransaksiTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeTransaksiTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodeTransaksiTextActionPerformed

    private void tanggalTransaksiTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tanggalTransaksiTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tanggalTransaksiTextActionPerformed

    private void kodeBarangTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeBarangTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodeBarangTextActionPerformed

    private void jumlahJualTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahJualTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahJualTextActionPerformed

    private void insertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertButtonActionPerformed
        // TODO add your handling code here:
        //JOptionPane.showMessageDialog(this, "inserted");
        if (requestLock(usern)) {
            String kd_trns = kodeTransaksiText.getText();
            String tgl_trns = tanggalTransaksiText.getText();
            String kd_brg = kodeBarangText.getText();
            String jual = jumlahJualText.getText();

            try{
                String sql = "CALL insert_penjualan(?, ?, ?, ?);";
                PreparedStatement stmt = c.prepareStatement(sql);
                stmt.setString(1, kd_trns);
                stmt.setString(2, tgl_trns);
                stmt.setString(3, kd_brg);
                stmt.setString(4, jual);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Inserted!");

                // Refresh the table
                enableSaveRoll(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed Command");
            }
        clearSet();
        }
    }//GEN-LAST:event_insertButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // TODO add your handling code here:
        if (requestLock(usern)) {
            String kd_trns = kodeTransaksiText.getText();
            String tgl_trns = tanggalTransaksiText.getText();
            String kd_brg = kodeBarangText.getText();
            String jual = jumlahJualText.getText();

            try{
                String sql = "CALL update_penjualan(?, ?, ?, ?);";
                PreparedStatement stmt = c.prepareStatement(sql);
                stmt.setString(1, kd_trns);
                stmt.setString(2, tgl_trns);
                stmt.setString(3, kd_brg);
                stmt.setString(4, jual);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Updated!");

                // Refresh the table
                enableSaveRoll(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed Command");
            }
            clearSet();
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        String kd_trns = kodeTransaksiText.getText();
        
        if (requestLock(usern)) {
            try{
                String sql = "CALL delete_penjualan(?);";
                PreparedStatement stmt = c.prepareStatement(sql);
                stmt.setString(1, kd_trns);
                stmt.executeUpdate();
            
                JOptionPane.showMessageDialog(this, "Deleted!");

                // Refresh the table
                enableSaveRoll(true);
            
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed Command");
            }
            clearSet();
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        try {
            c.rollback();
            
            String sql = "UPDATE lock_status SET is_locked = FALSE, locked_by = NULL, locked_at = NULL WHERE id = 2";
            PreparedStatement unlock = c.prepareStatement(sql);
            unlock.executeUpdate();
            
            c.commit();
            
            JOptionPane.showMessageDialog(this, "Cancelled!");
            editingData = false;
            enableSaveRoll(false);
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed Command");
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "UPDATE lock_status SET is_locked = FALSE, locked_by = NULL, locked_at = NULL WHERE id = 2";
            PreparedStatement unlock = c.prepareStatement(sql);
            unlock.executeUpdate();
            
            c.commit();
        
            JOptionPane.showMessageDialog(this, "Saved!");
            editingData = false;
            enableSaveRoll(false);
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed Command");
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
        clearSet();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void setUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setUserButtonActionPerformed
        // TODO add your handling code here:
        String nname = JOptionPane.showInputDialog(this,"Set username as:");
        usern = nname;
    }//GEN-LAST:event_setUserButtonActionPerformed

    private void displayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayButtonActionPerformed
        // TODO add your handling code here:
        if (ruser != null){
            new tabelJual(ruser,rpass).setVisible(true);
        }else{
            new tabelJual().setVisible(true);
        }
        
    }//GEN-LAST:event_displayButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        LocalDate today = LocalDate.now();
        String tanggalFormat = today.toString(); // format default = YYYY-MM-DD
        tanggalTransaksiText.setText(tanggalFormat);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jualMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jualMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jualMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jualMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jualMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton displayButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton insertButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jumlahJualText;
    private javax.swing.JTextField kodeBarangText;
    private javax.swing.JTextField kodeTransaksiText;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton setUserButton;
    private javax.swing.JLabel statusText;
    private javax.swing.JTextField tanggalTransaksiText;
    // End of variables declaration//GEN-END:variables
}
