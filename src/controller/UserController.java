/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Dekwaii
 */

import java.sql.*;
import koneksi.DatabaseConnection;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserController {
    // Method khusus untuk memproses query login
    public User validasiLogin(String username, String password) {
        User userDitemukan = null;
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        
        try {
            Connection conn = DatabaseConnection.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Jika data ada, masukkan hasilnya ke dalam Objek Model User
                userDitemukan = new User();
                userDitemukan.setIdUser(rs.getInt("id_user"));
                userDitemukan.setUsername(rs.getString("username"));
                userDitemukan.setNamaLengkap(rs.getString("nama_lengkap"));
                userDitemukan.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.err.println("Error Query Login: " + e.getMessage());
        }
        
        return userDitemukan; // Mengembalikan objek user (atau null jika gagal)
    }
    
    // Fungsi untuk menampilkan data pegawai
    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        String query = "SELECT * FROM user ORDER BY id_user ASC";
        
        try (Connection conn = DatabaseConnection.getKoneksi(); // Disamakan pakai getKoneksi()
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getInt("id_user"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNamaLengkap(rs.getString("nama_lengkap"));
                user.setNoHp(rs.getString("no_hp"));
                user.setRole(rs.getString("role"));
                listUser.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error Get All Users: " + e.getMessage());
        }
        return listUser;
    }
    
    // Fungsi untuk tambah data User
    public boolean insertUser(User user) {
        String query = "INSERT INTO user (username, password, nama_lengkap, no_hp, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNamaLengkap());
            ps.setString(4, user.getNoHp());
            ps.setString(5, user.getRole());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Insert User: " + e.getMessage());
            return false;
        }
    }
    
    // Fungsi untuk mengedit data User
    public boolean updateUser(User user) {
        String query = "UPDATE user SET username=?, password=?, nama_lengkap=?, no_hp=?, role=? WHERE id_user=?";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNamaLengkap());
            ps.setString(4, user.getNoHp());
            ps.setString(5, user.getRole());
            ps.setInt(6, user.getIdUser());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Update User: " + e.getMessage());
            return false;
        }
    }
    
    // Fungsi untuk menghapus data User
    public boolean deleteUser(int idUser) {
        String query = "DELETE FROM user WHERE id_user=?";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, idUser);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Delete User: " + e.getMessage());
            return false;
        }
    }
    
    public String getPasswordById(int idUser) {
        String sql = "SELECT password FROM user WHERE id_user=?";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error Get Password: " + e.getMessage());
        }
        return "";
    }
}
