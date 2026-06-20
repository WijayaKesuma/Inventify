/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

/**
 *
 * @author Dekwaii
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection koneksi;
    
    public static Connection getKoneksi() {
        try {
            String url = "jdbc:mysql://localhost:3306/db_inventify";
            String user = "root";
            String password = "";
            
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            koneksi = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi ke database db_inventify BERHASIL!");
        } catch (SQLException e) {
            System.err.println("Koneksi ke database GAGAL: " + e.getMessage());
        }
        return koneksi;
    }
}
