/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;


import koneksi.DatabaseConnection;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
 
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.NumberFormat;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import util.TableDecorator;

/**
 *
 * @author Dekwaii
 */
public class PanelReport extends javax.swing.JPanel {

    private DefaultTableModel tableModel;
    private boolean isLoading = false;
    
    /**
     * Creates new form PanelReport
     */
    public PanelReport() {
        initComponents();
        setupTable();
        loadFilterBulanTahun();
    }
    
    // =========================================================
    // 1. SETUP TABEL
    // =========================================================
    private void setupTable() {
        tableModel = new DefaultTableModel(
            new String[]{"No.", "Nama Supplier", "Nama Sepatu", "Stok", "Total Harga"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableReport.setModel(tableModel);
        TableDecorator.decorateTableReport(tableReport);
    }
    
    // =========================================================
    // 2. LOAD FILTER BULAN & TAHUN (hanya yang ada transaksinya)
    // =========================================================
    private void loadFilterBulanTahun() {
        isLoading = true;
 
        CBbulan.removeAllItems();
        CBtahun.removeAllItems();
 
        String[] namaBulan = {
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        };
 
        String query = "SELECT DISTINCT MONTH(tanggal) as bulan, YEAR(tanggal) as tahun " +
                       "FROM stok_masuk WHERE tanggal IS NOT NULL " +
                       "ORDER BY tahun DESC, bulan ASC";
 
        try (Connection conn = DatabaseConnection.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
 
            Set<String> bulanSet = new LinkedHashSet<>();
            Set<String> tahunSet = new LinkedHashSet<>();
 
            while (rs.next()) {
                int bulanAngka = rs.getInt("bulan");
                String tahun = String.valueOf(rs.getInt("tahun"));
                bulanSet.add(bulanAngka + "|" + namaBulan[bulanAngka - 1]);
                tahunSet.add(tahun);
            }
 
            for (String b : bulanSet) {
                String[] parts = b.split("\\|");
                CBbulan.addItem(parts[1]);
            }
 
            for (String t : tahunSet) {
                CBtahun.addItem(t);
            }
 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat filter: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
 
        isLoading = false;
        loadDataReport();
    }
    
    private void loadDataReport() {
        tableModel.setRowCount(0);
        totalModal.setText("0");
 
        if (CBbulan.getSelectedItem() == null || CBtahun.getSelectedItem() == null) return;
 
        String selectedBulan = (String) CBbulan.getSelectedItem();
        String selectedTahun = (String) CBtahun.getSelectedItem();
 
        String[] namaBulan = {
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        };
 
        int bulanAngka = 0;
        for (int i = 0; i < namaBulan.length; i++) {
            if (namaBulan[i].equals(selectedBulan)) {
                bulanAngka = i + 1;
                break;
            }
        }
 
        String query = "SELECT s.nama_supplier, sp.nama_sepatu, sm.jumlah, sm.total_harga " +
                       "FROM stok_masuk sm " +
                       "JOIN supplier s ON sm.id_supplier = s.id_supplier " +
                       "JOIN sepatu sp ON sm.id_sepatu = sp.id_sepatu " +
                       "WHERE MONTH(sm.tanggal) = ? AND YEAR(sm.tanggal) = ? " +
                       "ORDER BY sm.id_masuk ASC";
 
        try (Connection conn = DatabaseConnection.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(query)) {
 
            ps.setInt(1, bulanAngka);
            ps.setInt(2, Integer.parseInt(selectedTahun));
 
            ResultSet rs = ps.executeQuery();
 
            double grandTotal = 0;
            int no = 1;
            NumberFormat formatRupiah = NumberFormat.getInstance(new Locale("id", "ID"));
 
            while (rs.next()) {
                String namaSupplier = rs.getString("nama_supplier");
                String namaSepatu   = rs.getString("nama_sepatu");
                int stok            = rs.getInt("jumlah");
                double totalHarga   = rs.getDouble("total_harga");
 
                tableModel.addRow(new Object[]{
                    no++,
                    namaSupplier,
                    namaSepatu,
                    stok,
                    "Rp " + formatRupiah.format(totalHarga)
                });
 
                grandTotal += totalHarga;
            }
 
            totalModal.setText("Rp " + formatRupiah.format(grandTotal));
 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // =========================================================
    // 4. EKSPORT PDF MENGGUNAKAN ITEXT 5
    // =========================================================
    private void eksportPDF() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diekspor!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Laporan PDF");
        fileChooser.setSelectedFile(new java.io.File(
            "Laporan_" + CBbulan.getSelectedItem() + "_" + CBtahun.getSelectedItem() + ".pdf"
        ));
 
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection != JFileChooser.APPROVE_OPTION) return;
 
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.endsWith(".pdf")) filePath += ".pdf";
 
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
 
            // Judul
            Font fontJudul = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph judul = new Paragraph(
                "Laporan Stok Masuk - " + CBbulan.getSelectedItem() + " " + CBtahun.getSelectedItem(),
                fontJudul
            );
            judul.setAlignment(Element.ALIGN_CENTER);
            judul.setSpacingAfter(20f);
            document.add(judul);
 
            // Tabel PDF
            PdfPTable pdfTable = new PdfPTable(5);
            pdfTable.setWidthPercentage(100);
            pdfTable.setWidths(new float[]{0.5f, 2.5f, 2.5f, 1f, 2f});
 
            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
            BaseColor headerColor = new BaseColor(6, 25, 81);
 
            String[] headers = {"No.", "Nama Supplier", "Nama Sepatu", "Stok", "Total Harga"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fontHeader));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(8f);
                pdfTable.addCell(cell);
            }
 
            Font fontIsi = new Font(Font.FontFamily.HELVETICA, 10);
            BaseColor warnaBaris1 = BaseColor.WHITE;
            BaseColor warnaBaris2 = new BaseColor(220, 230, 255);
 
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                BaseColor warnaRow = (i % 2 == 0) ? warnaBaris1 : warnaBaris2;
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    String nilai = tableModel.getValueAt(i, j).toString();
                    PdfPCell cell = new PdfPCell(new Phrase(nilai, fontIsi));
                    cell.setBackgroundColor(warnaRow);
                    cell.setPadding(6f);
                    if (j == 0 || j == 3) {
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    } else if (j == 4) {
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    } else {
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    }
                    pdfTable.addCell(cell);
                }
            }
 
            document.add(pdfTable);
 
            // Total Modal
            Font fontTotal = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph total = new Paragraph(
                "\nTotal Modal : " + totalModal.getText(),
                fontTotal
            );
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);
 
            document.close();
 
            JOptionPane.showMessageDialog(this,
                "Laporan berhasil diekspor ke:\n" + filePath,
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Gagal mengekspor PDF: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        CBbulan = new javax.swing.JComboBox<>();
        CBtahun = new javax.swing.JComboBox<>();
        buttonEksport = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        totalModal = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReport = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(960, 680));
        setMinimumSize(new java.awt.Dimension(960, 680));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Form Report");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, 29));

        CBbulan.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        CBbulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bulan" }));
        CBbulan.addActionListener(this::CBbulanActionPerformed);
        jPanel1.add(CBbulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 162, 41));

        CBtahun.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        CBtahun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tahun" }));
        CBtahun.addActionListener(this::CBtahunActionPerformed);
        jPanel1.add(CBtahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 158, 40));

        buttonEksport.setBackground(new java.awt.Color(6, 25, 81));
        buttonEksport.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        buttonEksport.setForeground(new java.awt.Color(255, 255, 255));
        buttonEksport.setText("Eksport Laporan");
        buttonEksport.addActionListener(this::buttonEksportActionPerformed);
        jPanel1.add(buttonEksport, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 160, 40));

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("Total Modal :");

        totalModal.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        totalModal.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalModal, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(totalModal))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No.", "Nama Supplier", "Nama Sepatu", "Stok", "Total Harga"
            }
        ));
        jScrollPane1.setViewportView(tableReport);
        if (tableReport.getColumnModel().getColumnCount() > 0) {
            tableReport.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 20, 880, 440));

        add(jPanel3, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void CBbulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBbulanActionPerformed
        // TODO add your handling code here:
        if (!isLoading) loadDataReport();
    }//GEN-LAST:event_CBbulanActionPerformed

    private void CBtahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBtahunActionPerformed
        // TODO add your handling code here:
        if (!isLoading) loadDataReport();
    }//GEN-LAST:event_CBtahunActionPerformed

    private void buttonEksportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEksportActionPerformed
        // TODO add your handling code here:
        eksportPDF();
    }//GEN-LAST:event_buttonEksportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBbulan;
    private javax.swing.JComboBox<String> CBtahun;
    private javax.swing.JButton buttonEksport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableReport;
    private javax.swing.JLabel totalModal;
    // End of variables declaration//GEN-END:variables
}
