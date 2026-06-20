/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inventify;

/**
 *
 * @author Dekwaii
 */

import com.formdev.flatlaf.FlatLightLaf;
import view.LoginFrame;

public class Inventify {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        koneksi.DatabaseConnection.getKoneksi();
        
        //Set tema modern FlatLaf (Biar UI langsung rounded/aesthetic)
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Gagal memuat tema FlatLaf.");
        }

        //Memanggil dan memunculkan LoginFrame
        LoginFrame login = new view.LoginFrame();
        login.setLocationRelativeTo(null); // Membuat frame muncul pas di tengah layar
        login.setVisible(true); // Memunculkan frame
    }
    
}
