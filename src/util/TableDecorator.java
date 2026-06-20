/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Dekwaii
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


public class TableDecorator {
    // FUNGSI BARU KHUSUS UNTUK DEKORASI VISUAL TABEL
    public static void decorateTablePegawai(JTable tablePegawai) {
        // Set visual grid & tinggi baris
        tablePegawai.setShowGrid(true);
        tablePegawai.setGridColor(new Color(220, 220, 220)); 
        tablePegawai.setShowHorizontalLines(true);
        tablePegawai.setShowVerticalLines(true);
        tablePegawai.setIntercellSpacing(new Dimension(1, 1));
        tablePegawai.setRowHeight(30);

        // Kunci ukuran lebar masing-masing kolom
        tablePegawai.getColumnModel().getColumn(0).setPreferredWidth(50);   
        tablePegawai.getColumnModel().getColumn(1).setPreferredWidth(150);  
        tablePegawai.getColumnModel().getColumn(2).setPreferredWidth(250);  
        tablePegawai.getColumnModel().getColumn(3).setPreferredWidth(150);  
        tablePegawai.getColumnModel().getColumn(4).setPreferredWidth(150);  
        
        applyRenderers(tablePegawai);
    }
    
    public static void decorateTableSepatu(JTable tableSepatu) {
        // Set visual grid & tinggi baris
        tableSepatu.setShowGrid(true);
        tableSepatu.setGridColor(new Color(220, 220, 220)); 
        tableSepatu.setShowHorizontalLines(true);
        tableSepatu.setShowVerticalLines(true);
        tableSepatu.setIntercellSpacing(new Dimension(1, 1));
        tableSepatu.setRowHeight(30);

        // Kunci ukuran lebar masing-masing kolom
        tableSepatu.getColumnModel().getColumn(0).setPreferredWidth(100);   
        tableSepatu.getColumnModel().getColumn(1).setPreferredWidth(300); 
        tableSepatu.getColumnModel().getColumn(2).setPreferredWidth(180);  
        tableSepatu.getColumnModel().getColumn(3).setPreferredWidth(100); 
        tableSepatu.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        applyRenderers(tableSepatu);
    }
    
    public static void decorateTableSupplier(JTable tableSupplier) {
        // Set visual grid & tinggi baris
        tableSupplier.setShowGrid(true);
        tableSupplier.setGridColor(new Color(220, 220, 220)); 
        tableSupplier.setShowHorizontalLines(true);
        tableSupplier.setShowVerticalLines(true);
        tableSupplier.setIntercellSpacing(new Dimension(1, 1));
        tableSupplier.setRowHeight(30);

        // Kunci ukuran lebar masing-masing kolom
        tableSupplier.getColumnModel().getColumn(0).setPreferredWidth(80);  
        tableSupplier.getColumnModel().getColumn(1).setPreferredWidth(300);  
        tableSupplier.getColumnModel().getColumn(2).setPreferredWidth(180);  
        tableSupplier.getColumnModel().getColumn(3).setPreferredWidth(400); 

        applyRenderers(tableSupplier);
    }
    
    public static void decorateTableStok(JTable tableStokMasuk) {
        // Set visual grid & tinggi baris
        tableStokMasuk.setShowGrid(true);
        tableStokMasuk.setGridColor(new Color(220, 220, 220)); 
        tableStokMasuk.setShowHorizontalLines(true);
        tableStokMasuk.setShowVerticalLines(true);
        tableStokMasuk.setIntercellSpacing(new Dimension(1, 1));
        tableStokMasuk.setRowHeight(30);

        // Kunci ukuran lebar masing-masing kolom
        tableStokMasuk.getColumnModel().getColumn(0).setPreferredWidth(50);   
        tableStokMasuk.getColumnModel().getColumn(1).setPreferredWidth(200);  
        tableStokMasuk.getColumnModel().getColumn(2).setPreferredWidth(100);  
        tableStokMasuk.getColumnModel().getColumn(3).setPreferredWidth(50);  
        tableStokMasuk.getColumnModel().getColumn(4).setPreferredWidth(100);  
        tableStokMasuk.getColumnModel().getColumn(5).setPreferredWidth(150); 
        
        applyRenderers(tableStokMasuk);
    }
    
    
    private static void applyRenderers(JTable table){
        // Renderer untuk Padding Kiri-Kanan 10px
        DefaultTableCellRenderer paddingRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); 
                return this;
            }
        };

        // Renderer untuk ID (Rata Tengah + Padding)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER); 
                return this;
            }
        };

        // 1. Kolom pertama (Indeks 0) selalu ID, set ke rata tengah
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        // 2. Sisa kolom lainnya otomatis diberi padding secara dinamis sesuai total kolom tabel
        int totalKolom = table.getColumnCount();
        for (int i = 1; i < totalKolom; i++) {
            
            // BONUS LOGIKA CERDAS: Kalau ini tabel Sepatu, kolom Ukuran (indeks 3) & Stok (indeks 4) dibuat rata tengah juga biar rapi!
            if (table.getName() != null && table.getName().equals("tableSepatu") && (i == 3 || i == 4)) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            } else {
                table.getColumnModel().getColumn(i).setCellRenderer(paddingRenderer);
            }
        }
    }
}
