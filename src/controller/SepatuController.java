/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Dekwaii
 */

import koneksi.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Sepatu;

public class SepatuController {
    // Create (Insert)
    public boolean insertSepatu(Sepatu s) {
        String sql = "INSERT INTO sepatu (id_sepatu, nama_sepatu, brand, ukuran, stok) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getIdSepatu());
            ps.setString(2, s.getNamaSepatu());
            ps.setString(3, s.getBrand());
            ps.setInt(4, s.getUkuran());
            ps.setInt(5, s.getStok());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal tambah sepatu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Read (Get All)
    public List<Sepatu> getAllSepatu() {
        List<Sepatu> list = new ArrayList<>();
        String sql = "SELECT * FROM sepatu";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Sepatu s = new Sepatu(
                    rs.getString("id_sepatu"),
                    rs.getString("nama_sepatu"),
                    rs.getString("brand"),
                    rs.getInt("ukuran"),
                    rs.getInt("stok")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal ambil data sepatu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    // Update
    public boolean updateSepatu(Sepatu s) {
        String sql = "UPDATE sepatu SET nama_sepatu=?, brand=?, ukuran=?, stok=? WHERE id_sepatu=?";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNamaSepatu());
            ps.setString(2, s.getBrand());
            ps.setInt(3, s.getUkuran());
            ps.setInt(4, s.getStok());
            ps.setString(5, s.getIdSepatu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal update sepatu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Delete
    public boolean deleteSepatu(String idSepatu) {
        String sql = "DELETE FROM sepatu WHERE id_sepatu=?";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idSepatu);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal hapus sepatu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    //Generate ID sepatu
    public String generateIdSepatu() {
        String sql = "SELECT id_sepatu FROM sepatu ORDER BY id_sepatu DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastId = rs.getString("id_sepatu"); // contoh: "SPT003"
                int angka = Integer.parseInt(lastId.substring(3)); // ambil "003" -> 3
                angka++;
                return String.format("SPT%03d", angka); // jadi "SPT004"
            } else {
                return "SPT001"; // kalau tabel masih kosong
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal generate ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
