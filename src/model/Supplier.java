/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dekwaii
 */
public class Supplier {
    private int idSupplier;
    private String namaSupplier;
    private String noTelp;
    private String alamat;

    public Supplier() {
    }

    public Supplier(int idSupplier, String namaSupplier, String noTelp, String alamat) {
        this.idSupplier = idSupplier;
        this.namaSupplier = namaSupplier;
        this.noTelp = noTelp;
        this.alamat = alamat;
    }

    public int getIdSupplier() {
        return idSupplier;
    }
    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }
    public String getNamaSupplier() {
        return namaSupplier;
    }
    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }
    public String getNoTelp() {
        return noTelp;
    }
    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
