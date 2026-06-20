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
import model.StokMasuk;

public class StokMasukController {
    // Insert stok masuk + update stok sepatu (pakai transaction)
    public boolean tambahStokMasuk(StokMasuk sm) {
        String sqlInsert = "INSERT INTO stok_masuk (id_supplier, id_sepatu, jumlah, harga_beli, total_harga) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdateStok = "UPDATE sepatu SET stok = stok + ? WHERE id_sepatu = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getKoneksi();
            conn.setAutoCommit(false); // mulai transaction

            try (PreparedStatement ps1 = conn.prepareStatement(sqlInsert)) {
                ps1.setInt(1, sm.getIdSupplier());
                ps1.setString(2, sm.getIdSepatu());
                ps1.setInt(3, sm.getJumlah());
                ps1.setDouble(4, sm.getHargaBeli());
                ps1.setDouble(5, sm.getTotalHarga());
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement(sqlUpdateStok)) {
                ps2.setInt(1, sm.getJumlah());
                ps2.setString(2, sm.getIdSepatu());
                ps2.executeUpdate();
            }

            conn.commit(); // kalau dua-duanya sukses, baru disimpan permanen
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // kalau ada yang gagal, batalkan semua
            } catch (SQLException ex) {
                System.err.println("Rollback gagal: " + ex.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Gagal menambah stok: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Ambil semua histori stok masuk, JOIN supaya dapat nama supplier & nama sepatu
    public List<StokMasuk> getAllStokMasuk() {
        List<StokMasuk> list = new ArrayList<>();
        String sql = "SELECT sm.*, s.nama_supplier, sp.nama_sepatu " +
                     "FROM stok_masuk sm " +
                     "LEFT JOIN supplier s ON sm.id_supplier = s.id_supplier " +
                     "LEFT JOIN sepatu sp ON sm.id_sepatu = sp.id_sepatu " +
                     "ORDER BY sm.id_masuk DESC";

        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                StokMasuk sm = new StokMasuk();
                sm.setIdMasuk(rs.getInt("id_masuk"));
                sm.setTanggal(rs.getTimestamp("tanggal"));
                sm.setIdSupplier(rs.getInt("id_supplier"));
                sm.setIdSepatu(rs.getString("id_sepatu"));
                sm.setJumlah(rs.getInt("jumlah"));
                sm.setHargaBeli(rs.getDouble("harga_beli"));
                sm.setTotalHarga(rs.getDouble("total_harga"));
                sm.setNamaSupplier(rs.getString("nama_supplier"));
                sm.setNamaSepatu(rs.getString("nama_sepatu"));
                list.add(sm);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal ambil data stok masuk: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
    // Ambil 1 data stok masuk by id (buat keperluan hitung selisih saat edit/delete)
    public StokMasuk getStokMasukById(int idMasuk) {
        String sql = "SELECT * FROM stok_masuk WHERE id_masuk=?";
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMasuk);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    StokMasuk sm = new StokMasuk();
                    sm.setIdMasuk(rs.getInt("id_masuk"));
                    sm.setIdSupplier(rs.getInt("id_supplier"));
                    sm.setIdSepatu(rs.getString("id_sepatu"));
                    sm.setJumlah(rs.getInt("jumlah"));
                    sm.setHargaBeli(rs.getDouble("harga_beli"));
                    sm.setTotalHarga(rs.getDouble("total_harga"));
                    return sm;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error Get StokMasuk By Id: " + e.getMessage());
        }
        return null;
    }
    
    // Edit jumlah/harga, sekaligus koreksi selisih stok di tabel sepatu
    public boolean editStokMasuk(int idMasuk, int jumlahBaru, double hargaBaruBeli) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getKoneksi();
            conn.setAutoCommit(false);

            // Ambil data lama dulu buat tau selisihnya
            StokMasuk lama = getStokMasukById(idMasuk);
            if (lama == null) {
                conn.rollback();
                return false;
            }

            int selisihJumlah = jumlahBaru - lama.getJumlah();
            double totalBaru = jumlahBaru * hargaBaruBeli;

            // Update row histori
            String sqlUpdate = "UPDATE stok_masuk SET jumlah=?, harga_beli=?, total_harga=? WHERE id_masuk=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setInt(1, jumlahBaru);
                ps.setDouble(2, hargaBaruBeli);
                ps.setDouble(3, totalBaru);
                ps.setInt(4, idMasuk);
                ps.executeUpdate();
            }

            // Sesuaikan stok sepatu sebesar selisihnya saja (bisa positif atau negatif)
            String sqlUpdateStok = "UPDATE sepatu SET stok = stok + ? WHERE id_sepatu = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateStok)) {
                ps.setInt(1, selisihJumlah);
                ps.setString(2, lama.getIdSepatu());
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback gagal: " + ex.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Gagal edit stok: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (conn != null) { conn.setAutoCommit(true); conn.close(); }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Delete histori, sekaligus kurangi balik stok sepatu yang udah ke-+ sebelumnya
    public boolean deleteStokMasuk(int idMasuk) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getKoneksi();
            conn.setAutoCommit(false);

            StokMasuk data = getStokMasukById(idMasuk);
            if (data == null) {
                conn.rollback();
                return false;
            }

            String sqlDelete = "DELETE FROM stok_masuk WHERE id_masuk=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDelete)) {
                ps.setInt(1, idMasuk);
                ps.executeUpdate();
            }

            String sqlUpdateStok = "UPDATE sepatu SET stok = stok - ? WHERE id_sepatu = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateStok)) {
                ps.setInt(1, data.getJumlah());
                ps.setString(2, data.getIdSepatu());
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback gagal: " + ex.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Gagal hapus stok: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (conn != null) { conn.setAutoCommit(true); conn.close(); }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
