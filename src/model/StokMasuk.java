/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dekwaii
 */

import java.sql.Timestamp;

public class StokMasuk {
    private int idMasuk;
    private Timestamp tanggal;
    private int idSupplier;
    private String idSepatu;
    private int jumlah;
    private double hargaBeli;
    private double totalHarga;

    // field tambahan buat ditampilkan di tabel (hasil JOIN, bukan kolom asli stok_masuk)
    private String namaSupplier;
    private String namaSepatu;

    public StokMasuk() {
    }

    public StokMasuk(int idSupplier, String idSepatu, int jumlah, double hargaBeli, double totalHarga) {
        this.idSupplier = idSupplier;
        this.idSepatu = idSepatu;
        this.jumlah = jumlah;
        this.hargaBeli = hargaBeli;
        this.totalHarga = totalHarga;
    }

    public int getIdMasuk() {
        return idMasuk;
    }
    public void setIdMasuk(int idMasuk) {
        this.idMasuk = idMasuk;
    }
    public Timestamp getTanggal() {
        return tanggal;
    }
    public void setTanggal(Timestamp tanggal) {
        this.tanggal = tanggal;
    }
    public int getIdSupplier() {
        return idSupplier;
    }
    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }
    public String getIdSepatu() {
        return idSepatu;
    }
    public void setIdSepatu(String idSepatu) {
        this.idSepatu = idSepatu;
    }
    public int getJumlah() {
        return jumlah;
    }
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
    public double getHargaBeli() {
        return hargaBeli;
    }
    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }
    public double getTotalHarga() {
        return totalHarga;
    }
    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }
    public String getNamaSupplier() {
        return namaSupplier;
    }
    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }
    public String getNamaSepatu() {
        return namaSepatu;
    }
    public void setNamaSepatu(String namaSepatu) {
        this.namaSepatu = namaSepatu;
    }
}
