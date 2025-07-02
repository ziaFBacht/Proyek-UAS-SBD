/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.uas_sbd;

import javax.swing.*;

/**
 *
 * @author User
 */
public class UAS_SBD {

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {
                try {
                    new loginMenu().setVisible(true);
                } catch (Exception e) {
                    showError("GUI init error", e);
                }
            });
        } catch (Throwable t) {
            showError("Startup error", t);
        }
    }

    private static void showError(String title, Throwable e) {
        JOptionPane.showMessageDialog(null,
            title + ":\n" + e.toString(),
            "Fatal Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
