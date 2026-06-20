/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dekwaii
 */
public class Sepatu {
    private String idSepatu; 
    private String namaSepatu;
    private String brand;
    private int ukuran;
    private int stok;

    public Sepatu() {
    }

    public Sepatu(String idSepatu, String namaSepatu, String brand, int ukuran, int stok) {
        this.idSepatu = idSepatu;
        this.namaSepatu = namaSepatu;
        this.brand = brand;
        this.ukuran = ukuran;
        this.stok = stok;
    }

    public String getIdSepatu() { 
        return idSepatu; 
    }
    public void setIdSepatu(String idSepatu) { 
        this.idSepatu = idSepatu; 
    }

    public String getNamaSepatu() { 
        return namaSepatu; 
    }
    public void setNamaSepatu(String namaSepatu) { 
        this.namaSepatu = namaSepatu; 
    }

    public String getBrand() { 
        return brand; 
    }
    public void setBrand(String brand) { 
        this.brand = brand; 
    }

    public int getUkuran() { 
        return ukuran; 
    }
    public void setUkuran(int ukuran) { 
        this.ukuran = ukuran; 
    }

    public int getStok() { 
        return stok; 
    }
    public void setStok(int stok) { 
        this.stok = stok; 
    }
}
