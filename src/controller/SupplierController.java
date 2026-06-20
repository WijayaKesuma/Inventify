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
import model.Supplier;

public class SupplierController {
    // Create
    public boolean insertSupplier(Supplier s) {
        String sql = "INSERT INTO supplier (nama_supplier, no_telp, alamat) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNamaSupplier());
            ps.setString(2, s.getNoTelp());
            ps.setString(3, s.getAlamat());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal tambah supplier: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Read
    public List<Supplier> getAllSupplier() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier ORDER BY id_supplier ASC";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Supplier s = new Supplier(
                    rs.getInt("id_supplier"),
                    rs.getString("nama_supplier"),
                    rs.getString("no_telp"),
                    rs.getString("alamat")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal ambil data supplier: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    // Update
    public boolean updateSupplier(Supplier s) {
        String sql = "UPDATE supplier SET nama_supplier=?, no_telp=?, alamat=? WHERE id_supplier=?";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNamaSupplier());
            ps.setString(2, s.getNoTelp());
            ps.setString(3, s.getAlamat());
            ps.setInt(4, s.getIdSupplier());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal update supplier: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Delete
    public boolean deleteSupplier(int idSupplier) {
        String sql = "DELETE FROM supplier WHERE id_supplier=?";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSupplier);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal hapus supplier: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
