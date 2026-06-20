/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

/**
 *
 * @author Dekwaii
 */

import controller.UserController;
import controller.SepatuController;
import controller.SupplierController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Sepatu;
import model.Supplier;
import model.User;
import util.TableDecorator;

public class PanelKelolaData extends javax.swing.JPanel {

    private final UserController userController = new UserController();
    private final SepatuController sepatuController = new SepatuController();
    private final SupplierController supplierController = new SupplierController();
    
    private DefaultTableModel tableModelPegawai;
    private DefaultTableModel tableModelSepatu;
    private DefaultTableModel tableModelSupplier;

    
    private int selectedUserId = -1;
    private String selectedSepatuId = "";
    private int selectedSupplierId = -1;
    private String selectedUserPasswordLama = ""; 
    
    public PanelKelolaData() {
        initComponents();
    
        loadTablePegawai();
        loadTableSepatu();
        loadTableSupplier();
        
        txtStok.setEditable(false);
        
        generateAndSetIdSepatu();
    }
    
    private void generateAndSetIdSepatu() {
        String idBaru = sepatuController.generateIdSepatu();
        txtIdSepatu.setText(idBaru);
        txtIdSepatu.setEditable(false);
    }
    
    // 2. TAMBAHKAN METHOD BARU INI UNTUK MENANGKAP ROLE DARI DASHBOARD
    public void setHakAksesPanel(String roleLogin) {
        if(roleLogin == null){
            return;
        }
        
        if (roleLogin != null && roleLogin.equalsIgnoreCase("Admin Gudang")) {
            tabKelolaData.remove(0);
            loadTableSepatu();
        }else if(roleLogin.equalsIgnoreCase("Super Admin")){
            tabKelolaData.remove(1);
            tabKelolaData.remove(1);
            loadTablePegawai();
        }
    }
    
    //Load table User/Pegawai
    private void loadTablePegawai() {
        String[] judulKolom = {"ID", "Username", "Nama Lengkap", "No Hp", "Role"};
        tableModelPegawai = new DefaultTableModel(judulKolom, 0);

        List<User> listUser = userController.getAllUsers();
        for (User u : listUser) {
            Object[] rowData = {
                u.getIdUser(),
                u.getUsername(),
                u.getNamaLengkap(),
                u.getNoHp(),
                u.getRole()
            };
            tableModelPegawai.addRow(rowData);
        }
        tablePegawai.setModel(tableModelPegawai);
        TableDecorator.decorateTablePegawai(tablePegawai);
    }
    
    //Load table Sepatu
    private void loadTableSepatu(){
        String[] judulKolom = {"ID Sepatu", "Nama Sepatu", "Brand", "Ukuran", "Stok"};
        tableModelSepatu = new DefaultTableModel(judulKolom, 0);

        List<Sepatu> listSepatu = sepatuController.getAllSepatu();
        for (Sepatu s : listSepatu) {
            Object[] rowData = { 
                s.getIdSepatu(), 
                s.getNamaSepatu(), 
                s.getBrand(), 
                s.getUkuran(), 
                s.getStok() 
            };
            tableModelSepatu.addRow(rowData);
        }
        tableSepatu.setModel(tableModelSepatu);

        TableDecorator.decorateTableSepatu(tableSepatu);
    }
    
    //Load table Supplier
    private void loadTableSupplier() {
        String[] judulKolom = {"ID", "Nama Supplier", "No Telp", "Alamat/Kota"};
        tableModelSupplier = new DefaultTableModel(judulKolom, 0);

        List<Supplier> listSupplier = supplierController.getAllSupplier();
        for (Supplier s : listSupplier) {
            Object[] rowData = {
                s.getIdSupplier(),
                s.getNamaSupplier(),
                s.getNoTelp(),
                s.getAlamat()
            };
            tableModelSupplier.addRow(rowData);
        }
        tableSupplier.setModel(tableModelSupplier);
        TableDecorator.decorateTableSupplier(tableSupplier);
    }
    
    // FUNGSI MEMBERSIHKAN FORM INPUT SETELAH CRUD
    private void clearFormPegawai() {
        txtNamaLengkap.setText("");
        txtUsername.setText("");
        psdPassword.setText("");
        txtNoHp.setText("");
        selectedUserId = -1; 
        selectedUserPasswordLama = "";
    }
    
    private void clearFormSepatu() {
        txtIdSepatu.setText("");
        txtNamaSepatu.setText("");
        txtBrand.setText("");
        txtSize.setText("");
        selectedSepatuId = ""; 
        generateAndSetIdSepatu();
    }
    
    private void clearFormSupplier() {
        txtNamaSupplier.setText("");
        txtNoTelp.setText("");
        txtAlamat.setText("");
        selectedSupplierId = -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDeletePegawai = new javax.swing.JButton();
        tabKelolaData = new javax.swing.JTabbedPane();
        tabPegawai = new javax.swing.JPanel();
        panelFormPegawai = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNamaLengkap = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        psdPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        btnEditPegawai = new javax.swing.JButton();
        btnTambahPegawai = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtNoHp = new javax.swing.JTextField();
        btnDeletepegawai = new javax.swing.JButton();
        panelTablePegawai = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePegawai = new javax.swing.JTable();
        tabSepatu = new javax.swing.JPanel();
        panelFormSepatu = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIdSepatu = new javax.swing.JTextField();
        txtNamaSepatu = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtBrand = new javax.swing.JTextField();
        btnDeleteSepatu = new javax.swing.JButton();
        btnEditSepatu = new javax.swing.JButton();
        btnTambahSepatu = new javax.swing.JButton();
        txtSize = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        panelTableSepatu = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableSepatu = new javax.swing.JTable();
        tabSupplier = new javax.swing.JPanel();
        panelFormSupplier = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtNamaSupplier = new javax.swing.JTextField();
        txtNoTelp = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnDeleteSupplier = new javax.swing.JButton();
        btnEditSupplier = new javax.swing.JButton();
        btnTambahSupplier = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txtAlamat = new javax.swing.JTextField();
        panelTableSupplier = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSupplier = new javax.swing.JTable();

        btnDeletePegawai.setBackground(new java.awt.Color(241, 69, 69));
        btnDeletePegawai.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnDeletePegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnDeletePegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_delete.png"))); // NOI18N
        btnDeletePegawai.setText("Delete");
        btnDeletePegawai.addActionListener(this::btnDeletePegawaiActionPerformed);

        setMaximumSize(new java.awt.Dimension(960, 680));
        setMinimumSize(new java.awt.Dimension(960, 680));
        setPreferredSize(new java.awt.Dimension(960, 680));

        tabPegawai.setLayout(new java.awt.BorderLayout());

        panelFormPegawai.setPreferredSize(new java.awt.Dimension(960, 220));
        panelFormPegawai.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Nama Lengkap");
        panelFormPegawai.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 210, -1));
        panelFormPegawai.add(txtNamaLengkap, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 210, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Username");
        panelFormPegawai.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 210, 20));
        panelFormPegawai.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 210, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Password");
        panelFormPegawai.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, 210, -1));
        panelFormPegawai.add(psdPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 210, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Form Kelola Data Pegawai");
        panelFormPegawai.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 900, 40));

        btnEditPegawai.setBackground(new java.awt.Color(255, 187, 0));
        btnEditPegawai.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnEditPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnEditPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_edit.png"))); // NOI18N
        btnEditPegawai.setText("Edit");
        btnEditPegawai.addActionListener(this::btnEditPegawaiActionPerformed);
        panelFormPegawai.add(btnEditPegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 210, 40));

        btnTambahPegawai.setBackground(new java.awt.Color(12, 44, 137));
        btnTambahPegawai.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnTambahPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_tambah.png"))); // NOI18N
        btnTambahPegawai.setText("Tambah");
        btnTambahPegawai.addActionListener(this::btnTambahPegawaiActionPerformed);
        panelFormPegawai.add(btnTambahPegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 210, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("No Hp");
        panelFormPegawai.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, 210, -1));
        panelFormPegawai.add(txtNoHp, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, 210, 40));

        btnDeletepegawai.setBackground(new java.awt.Color(241, 69, 69));
        btnDeletepegawai.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnDeletepegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnDeletepegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_delete.png"))); // NOI18N
        btnDeletepegawai.setText("Delete");
        btnDeletepegawai.addActionListener(this::btnDeletepegawaiActionPerformed);
        panelFormPegawai.add(btnDeletepegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 210, 40));

        tabPegawai.add(panelFormPegawai, java.awt.BorderLayout.NORTH);

        panelTablePegawai.setPreferredSize(new java.awt.Dimension(960, 400));
        panelTablePegawai.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tablePegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Username", "Nama Lengkap", "No Hp", "Role"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePegawai.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        tablePegawai.setRequestFocusEnabled(false);
        tablePegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePegawaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablePegawai);
        if (tablePegawai.getColumnModel().getColumnCount() > 0) {
            tablePegawai.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablePegawai.getColumnModel().getColumn(1).setPreferredWidth(150);
            tablePegawai.getColumnModel().getColumn(2).setPreferredWidth(250);
            tablePegawai.getColumnModel().getColumn(3).setPreferredWidth(150);
            tablePegawai.getColumnModel().getColumn(4).setPreferredWidth(150);
        }

        panelTablePegawai.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 900, 390));

        tabPegawai.add(panelTablePegawai, java.awt.BorderLayout.CENTER);

        tabKelolaData.addTab("Pegawai", tabPegawai);

        tabSepatu.setLayout(new java.awt.BorderLayout());

        panelFormSepatu.setPreferredSize(new java.awt.Dimension(960, 220));
        panelFormSepatu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Form Kelola Data Sepatu");
        panelFormSepatu.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 900, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("ID Sepatu");
        panelFormSepatu.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 130, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Nama Sepatu");
        panelFormSepatu.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 230, 20));
        panelFormSepatu.add(txtIdSepatu, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 130, 40));

        txtNamaSepatu.addActionListener(this::txtNamaSepatuActionPerformed);
        panelFormSepatu.add(txtNamaSepatu, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 230, 40));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Brand");
        panelFormSepatu.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 220, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Size");
        panelFormSepatu.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 140, -1));
        panelFormSepatu.add(txtBrand, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 220, 40));

        btnDeleteSepatu.setBackground(new java.awt.Color(241, 69, 69));
        btnDeleteSepatu.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnDeleteSepatu.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteSepatu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_delete.png"))); // NOI18N
        btnDeleteSepatu.setText("Delete");
        btnDeleteSepatu.addActionListener(this::btnDeleteSepatuActionPerformed);
        panelFormSepatu.add(btnDeleteSepatu, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, 160, 40));

        btnEditSepatu.setBackground(new java.awt.Color(255, 187, 0));
        btnEditSepatu.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnEditSepatu.setForeground(new java.awt.Color(255, 255, 255));
        btnEditSepatu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_edit.png"))); // NOI18N
        btnEditSepatu.setText("Edit");
        btnEditSepatu.addActionListener(this::btnEditSepatuActionPerformed);
        panelFormSepatu.add(btnEditSepatu, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 160, 40));

        btnTambahSepatu.setBackground(new java.awt.Color(12, 44, 137));
        btnTambahSepatu.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnTambahSepatu.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahSepatu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_tambah.png"))); // NOI18N
        btnTambahSepatu.setText("Tambah");
        btnTambahSepatu.addActionListener(this::btnTambahSepatuActionPerformed);
        panelFormSepatu.add(btnTambahSepatu, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 160, 40));
        panelFormSepatu.add(txtSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 100, 140, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Stok");
        panelFormSepatu.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 70, -1, -1));

        txtStok.setText("0");
        panelFormSepatu.add(txtStok, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 100, 100, 40));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 51, 51));
        jLabel12.setText("<html>Note: Stok di set 0, dan tidak bisa di edit di halaman ini!. Silahkan tambah stok di halaman Stok Masuk</html>");
        panelFormSepatu.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 370, -1));

        tabSepatu.add(panelFormSepatu, java.awt.BorderLayout.NORTH);

        panelTableSepatu.setPreferredSize(new java.awt.Dimension(930, 500));
        panelTableSepatu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableSepatu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Sepatu", "Nama Sepatu", "Brand", "Ukuran", "Stok"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSepatu.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        tableSepatu.setMinimumSize(new java.awt.Dimension(75, 75));
        tableSepatu.setName("tableSepatu"); // NOI18N
        tableSepatu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSepatuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableSepatu);
        if (tableSepatu.getColumnModel().getColumnCount() > 0) {
            tableSepatu.getColumnModel().getColumn(0).setPreferredWidth(100);
            tableSepatu.getColumnModel().getColumn(1).setPreferredWidth(300);
            tableSepatu.getColumnModel().getColumn(2).setPreferredWidth(180);
            tableSepatu.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableSepatu.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        panelTableSepatu.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 900, 390));

        tabSepatu.add(panelTableSepatu, java.awt.BorderLayout.CENTER);

        tabKelolaData.addTab("Sepatu", tabSepatu);

        tabSupplier.setLayout(new java.awt.BorderLayout());

        panelFormSupplier.setPreferredSize(new java.awt.Dimension(960, 220));
        panelFormSupplier.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("No Telp");
        panelFormSupplier.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 210, 20));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Nama Supplier");
        panelFormSupplier.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 280, -1));
        panelFormSupplier.add(txtNamaSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 260, 40));
        panelFormSupplier.add(txtNoTelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 210, 40));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Alamat");
        panelFormSupplier.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 330, -1));

        btnDeleteSupplier.setBackground(new java.awt.Color(241, 69, 69));
        btnDeleteSupplier.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnDeleteSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_delete.png"))); // NOI18N
        btnDeleteSupplier.setText("Delete");
        btnDeleteSupplier.addActionListener(this::btnDeleteSupplierActionPerformed);
        panelFormSupplier.add(btnDeleteSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 210, 40));

        btnEditSupplier.setBackground(new java.awt.Color(255, 187, 0));
        btnEditSupplier.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnEditSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnEditSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_edit.png"))); // NOI18N
        btnEditSupplier.setText("Edit");
        btnEditSupplier.addActionListener(this::btnEditSupplierActionPerformed);
        panelFormSupplier.add(btnEditSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 210, 40));

        btnTambahSupplier.setBackground(new java.awt.Color(12, 44, 137));
        btnTambahSupplier.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnTambahSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ic_tambah.png"))); // NOI18N
        btnTambahSupplier.setText("Tambah");
        btnTambahSupplier.addActionListener(this::btnTambahSupplierActionPerformed);
        panelFormSupplier.add(btnTambahSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 210, 40));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel17.setText("Form Kelola Data Supplier");
        panelFormSupplier.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 900, 40));
        panelFormSupplier.add(txtAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, 290, 40));

        tabSupplier.add(panelFormSupplier, java.awt.BorderLayout.NORTH);

        panelTableSupplier.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nama Supplier", "No Telp", "Alamat/Kota"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSupplierMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableSupplier);
        if (tableSupplier.getColumnModel().getColumnCount() > 0) {
            tableSupplier.getColumnModel().getColumn(0).setPreferredWidth(80);
            tableSupplier.getColumnModel().getColumn(1).setPreferredWidth(300);
            tableSupplier.getColumnModel().getColumn(2).setPreferredWidth(180);
            tableSupplier.getColumnModel().getColumn(3).setPreferredWidth(400);
        }

        panelTableSupplier.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 900, 390));

        tabSupplier.add(panelTableSupplier, java.awt.BorderLayout.CENTER);

        tabKelolaData.addTab("Supplier", tabSupplier);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabKelolaData, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
                .addGap(113, 113, 113))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabKelolaData, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeletePegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePegawaiActionPerformed
        
    }//GEN-LAST:event_btnDeletePegawaiActionPerformed

    // Ini Bagian Sepatu (Delete, Edit) walaupun ada anomali Action 1
    private void btnDeleteSepatuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSepatuActionPerformed
        if (selectedSepatuId == null) {
            JOptionPane.showMessageDialog(this, "Pilih dulu data sepatu yang mau dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus data sepatu ini?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            boolean berhasil = sepatuController.deleteSepatu(selectedSepatuId);
            if (berhasil) {
                JOptionPane.showMessageDialog(this, "Data sepatu berhasil dihapus!");
                clearFormSepatu();
                loadTableSepatu();
            }
        }
    }//GEN-LAST:event_btnDeleteSepatuActionPerformed

    private void txtNamaSepatuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSepatuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaSepatuActionPerformed

    private void btnEditSepatuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSepatuActionPerformed
        if (selectedSepatuId == null) {
            JOptionPane.showMessageDialog(this, "Pilih dulu data sepatu yang mau diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nama = txtNamaSepatu.getText().trim();
        String brand = txtBrand.getText().trim();
        String sizeStr = txtSize.getText().trim();
        String stokStr = txtStok.getText().trim();

        if (nama.isEmpty() || brand.isEmpty() || sizeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ukuran, stok;
        try {
            ukuran = Integer.parseInt(sizeStr);
            stok = Integer.parseInt(stokStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Size/Stok harus berupa angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Ambil stok lama dari textfield (sudah ke-fill otomatis pas klik row, jadi aman walau read-only)
        int stokLama = Integer.parseInt(txtStok.getText().trim());

        Sepatu s = new Sepatu(selectedSepatuId, nama, brand, ukuran, stokLama);
        boolean berhasil = sepatuController.updateSepatu(s);

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Data sepatu berhasil diupdate!");
            clearFormSepatu();
            loadTableSepatu();
        }
    }//GEN-LAST:event_btnEditSepatuActionPerformed

    // Ini Bagian Supplier (Delete)
    private void btnDeleteSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSupplierActionPerformed
        if (selectedSupplierId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih dulu data supplier yang mau dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus data supplier ini?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            boolean berhasil = supplierController.deleteSupplier(selectedSupplierId);
            if (berhasil) {
                JOptionPane.showMessageDialog(this, "Data supplier berhasil dihapus!");
                clearFormSupplier();
                loadTableSupplier();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeleteSupplierActionPerformed

    // Ini bagian Kelola Data User/Pegawai
    private void tablePegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePegawaiMouseClicked
        int row = tablePegawai.getSelectedRow();
        if (row != -1) {
            DefaultTableModel model = (DefaultTableModel) tablePegawai.getModel();
            selectedUserId = Integer.parseInt(model.getValueAt(row, 0).toString());
            txtUsername.setText(model.getValueAt(row, 1).toString());
            txtNamaLengkap.setText(model.getValueAt(row, 2).toString());
            txtNoHp.setText(model.getValueAt(row, 3).toString());
            psdPassword.setText("");
            selectedUserPasswordLama = userController.getPasswordById(selectedUserId);
        }
    }//GEN-LAST:event_tablePegawaiMouseClicked

    private void btnTambahPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPegawaiActionPerformed
        String username = txtUsername.getText().trim();
        String password = psdPassword.getText().trim();
        String namaLengkap = txtNamaLengkap.getText().trim();
        String noHp = txtNoHp.getText().trim();

        if (username.isEmpty() || password.isEmpty() || namaLengkap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Lengkap, Username, dan Password wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User userBaru = new User(0, username, password, namaLengkap, noHp, "Admin Gudang");

        if (userController.insertUser(userBaru)) {
            JOptionPane.showMessageDialog(this, "Pegawai baru berhasil didaftarkan!");
            clearFormPegawai();
            loadTablePegawai();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTambahPegawaiActionPerformed

    private void btnEditPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditPegawaiActionPerformed
        if (selectedUserId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih dulu data pegawai yang mau diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = txtUsername.getText().trim();
        String passwordInput = psdPassword.getText().trim();
        String namaLengkap = txtNamaLengkap.getText().trim();
        String noHp = txtNoHp.getText().trim();

        if (username.isEmpty() || namaLengkap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Lengkap, dan Username wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String passwordFinal = passwordInput.isEmpty() ? selectedUserPasswordLama : passwordInput;
        
        User userEdit = new User(selectedUserId, username, passwordFinal, namaLengkap, noHp, "Admin Gudang");

        if (userController.updateUser(userEdit)) {
            JOptionPane.showMessageDialog(this, "Data pegawai berhasil diupdate!");
            clearFormPegawai();
            loadTablePegawai();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal update data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditPegawaiActionPerformed

    private void btnDeletepegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletepegawaiActionPerformed
        if (selectedUserId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih dulu data pegawai yang mau dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus data pegawai ini?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (userController.deleteUser(selectedUserId)) {
                JOptionPane.showMessageDialog(this, "Data pegawai berhasil dihapus!");
                clearFormPegawai();
                loadTablePegawai();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeletepegawaiActionPerformed

    //Ini Bagian Sepatu (Tambah, TableClicked)
    private void btnTambahSepatuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSepatuActionPerformed
        String id = txtIdSepatu.getText().trim();
        String nama = txtNamaSepatu.getText().trim();
        String brand = txtBrand.getText().trim();
        String sizeStr = txtSize.getText().trim();

        if (id.isEmpty() || nama.isEmpty() || brand.isEmpty() || sizeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ukuran;
        try {
            ukuran = Integer.parseInt(sizeStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Size harus berupa angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Stok selalu 0 saat tambah baru (sesuai note di form)
        Sepatu s = new Sepatu(id, nama, brand, ukuran, 0);
        boolean berhasil = sepatuController.insertSepatu(s);

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Data sepatu berhasil ditambahkan!");
            clearFormSepatu();
            loadTableSepatu();
        }
    }//GEN-LAST:event_btnTambahSepatuActionPerformed

    private void tableSepatuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSepatuMouseClicked
        // 1. Ambil indeks baris yang diklik oleh user
        int row = tableSepatu.getSelectedRow();

        if (row != -1) {
            // 2. Ambil model yang aktif di tabel
            DefaultTableModel model = (DefaultTableModel) tableSepatu.getModel();

            // 3. Pindahkan data kolom ke komponen Form (ID Sepatu berupa String, misal "SPT001")
            selectedSepatuId = model.getValueAt(row, 0).toString(); 

            txtNamaSepatu.setText(model.getValueAt(row, 1).toString());
            txtBrand.setText(model.getValueAt(row, 2).toString());
            txtSize.setText(model.getValueAt(row, 3).toString());
            txtStok.setText(model.getValueAt(row, 4).toString());
        }
    }//GEN-LAST:event_tableSepatuMouseClicked

    // Ini Bagian Supplier (Table Clicked, Tambah, Edit, Hapusnya diatas)
    private void tableSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSupplierMouseClicked
        int row = tableSupplier.getSelectedRow();
        if (row != -1) {
            DefaultTableModel model = (DefaultTableModel) tableSupplier.getModel();
            selectedSupplierId = Integer.parseInt(model.getValueAt(row, 0).toString());
            txtNamaSupplier.setText(model.getValueAt(row, 1).toString());
            txtNoTelp.setText(model.getValueAt(row, 2).toString());
            txtAlamat.setText(model.getValueAt(row, 3).toString());
        }
    }//GEN-LAST:event_tableSupplierMouseClicked

    private void btnTambahSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSupplierActionPerformed
        String nama = txtNamaSupplier.getText().trim();
        String noTelp = txtNoTelp.getText().trim();
        String alamat = txtAlamat.getText().trim();

        if (nama.isEmpty() || noTelp.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Supplier s = new Supplier(0, nama, noTelp, alamat);
        boolean berhasil = supplierController.insertSupplier(s);

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Data supplier berhasil ditambahkan!");
            clearFormSupplier();
            loadTableSupplier();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTambahSupplierActionPerformed

    private void btnEditSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSupplierActionPerformed
        if (selectedSupplierId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih dulu data supplier yang mau diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nama = txtNamaSupplier.getText().trim();
        String noTelp = txtNoTelp.getText().trim();
        String alamat = txtAlamat.getText().trim();

        if (nama.isEmpty() || noTelp.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Supplier s = new Supplier(selectedSupplierId, nama, noTelp, alamat);
        boolean berhasil = supplierController.updateSupplier(s);

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Data supplier berhasil diupdate!");
            clearFormSupplier();
            loadTableSupplier();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal update data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditSupplierActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeletePegawai;
    private javax.swing.JButton btnDeleteSepatu;
    private javax.swing.JButton btnDeleteSupplier;
    private javax.swing.JButton btnDeletepegawai;
    private javax.swing.JButton btnEditPegawai;
    private javax.swing.JButton btnEditSepatu;
    private javax.swing.JButton btnEditSupplier;
    private javax.swing.JButton btnTambahPegawai;
    private javax.swing.JButton btnTambahSepatu;
    private javax.swing.JButton btnTambahSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelFormPegawai;
    private javax.swing.JPanel panelFormSepatu;
    private javax.swing.JPanel panelFormSupplier;
    private javax.swing.JPanel panelTablePegawai;
    private javax.swing.JPanel panelTableSepatu;
    private javax.swing.JPanel panelTableSupplier;
    private javax.swing.JPasswordField psdPassword;
    private javax.swing.JTabbedPane tabKelolaData;
    private javax.swing.JPanel tabPegawai;
    private javax.swing.JPanel tabSepatu;
    private javax.swing.JPanel tabSupplier;
    private javax.swing.JTable tablePegawai;
    private javax.swing.JTable tableSepatu;
    private javax.swing.JTable tableSupplier;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtBrand;
    private javax.swing.JTextField txtIdSepatu;
    private javax.swing.JTextField txtNamaLengkap;
    private javax.swing.JTextField txtNamaSepatu;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtNoHp;
    private javax.swing.JTextField txtNoTelp;
    private javax.swing.JTextField txtSize;
    private javax.swing.JTextField txtStok;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
