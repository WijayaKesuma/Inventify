# Arsitektur Inventify

Aplikasi desktop Java Swing (NetBeans Ant project) untuk manajemen inventaris toko sepatu. Database MySQL `db_inventify` di `localhost:3306`, theme FlatLaf, arsitektur MVC sederhana (model / view / controller + lapisan koneksi).

## Lapisan & Tanggung Jawab

| Lapisan | Paket | Isi |
|---------|-------|-----|
| Entry | `inventify` | `Inventify.main()` — bootstrap koneksi + tema + buka login |
| Koneksi | `koneksi` | `DatabaseConnection.getKoneksi()` — provider `Connection` MySQL |
| View | `view` | `LoginFrame`, `MainDashboardFrame`, dan 5 panel konten |
| Dialog | `dialog` | `DialogSepatu`, `DialogSupplier` — picker untuk Stok Masuk |
| Controller | `controller` | `UserController`, `SepatuController`, `SupplierController`, `StokMasukController` |
| Model | `model` | `User`, `Sepatu`, `Supplier`, `StokMasuk` (POJO) |
| Util | `util` | `TableDecorator` — styling `JTable` (static) |

---

## 1. Alur Aplikasi Keseluruhan (Flowchart)

```mermaid
flowchart TD
    Start([JVM start]) --> Main["Inventify.main()"]
    Main --> Conn["DatabaseConnection.getKoneksi()<br/>jdbc:mysql://localhost:3306/db_inventify"]
    Conn --> Laf["FlatLightLaf.setup()"]
    Laf --> Login["Buka LoginFrame"]

    Login --> Input["User isi username + password<br/>klik LOGIN"]
    Input --> Empty{"Field kosong?"}
    Empty -->|Ya| WarnEmpty["Dialog: 'Tolong Username<br/>dan Passwordnya di isi Yaa!'"] --> Login
    Empty -->|Tidak| Validasi["UserController.validasiLogin()<br/>SELECT * FROM user<br/>WHERE username=? AND password=?"]

    Validasi --> Match{"User<br/>ditemukan?"}
    Match -->|Tidak| WarnFail["Dialog error:<br/>'Username atau Password salah!'"] --> Login
    Match -->|Ya| Welcome["Dialog selamat datang"] --> Dash["MainDashboardFrame()<br/>+ setHakAkses(user)"]

    Dash --> Role{"Cek role"}
    Role -->|Super Admin| RA["Tampil: Dashboard, Kelola Data,<br/>Report, About<br/>(sembunyikan Stok Masuk)"]
    Role -->|Admin Gudang| RB["Tampil: Dashboard, Kelola Data,<br/>Stok Masuk<br/>(sembunyikan Report, About)"]

    RA --> Card["CardLayout di panelKontenUtama<br/>tampilkan panelDashboard"]
    RB --> Card

    Card --> Nav{"Klik menu sidebar"}
    Nav -->|Dashboard| PD["PanelDashboard<br/>(placeholder)"]
    Nav -->|Kelola Data| PK["PanelKelolaData<br/>CRUD Pegawai/Sepatu/Supplier"]
    Nav -->|Stok Masuk| PS["PanelStokMasuk<br/>catat stok + update stok sepatu"]
    Nav -->|Report| PR["PanelReport<br/>(placeholder)"]
    Nav -->|About| PA["PanelAboutUs<br/>(placeholder)"]
    Nav -->|Logout| Logout["Konfirmasi → buka LoginFrame baru<br/>dispose() dashboard"]

    Logout --> Login
    PD --> Nav
    PK --> Nav
    PS --> Nav
    PR --> Nav
    PA --> Nav
```

> Catatan: `PanelDashboard`, `PanelReport`, dan `PanelAboutUs` saat ini masih placeholder (hanya satu `JLabel`, tanpa query DB).

---

## 2. Sequence — Login & Masuk Dashboard

```mermaid
sequenceDiagram
    actor U as User
    participant LF as LoginFrame
    participant UC as UserController
    participant DB as DatabaseConnection
    participant MySQL as MySQL (user)
    participant MD as MainDashboardFrame

    U->>LF: klik btnLogin
    LF->>LF: validasi field kosong
    alt field kosong
        LF-->>U: dialog "isi username & password"
    else terisi
        LF->>UC: validasiLogin(username, password)
        UC->>DB: getKoneksi()
        DB-->>UC: Connection
        UC->>MySQL: SELECT * FROM user<br/>WHERE username=? AND password=?
        MySQL-->>UC: ResultSet
        alt cocok
            UC-->>LF: User (idUser, username, namaLengkap, role)
            LF-->>U: dialog "Selamat Datang, {namaLengkap}"
            LF->>MD: new MainDashboardFrame()
            LF->>MD: setHakAkses(user)
            MD->>MD: filter menu by role + show Dashboard
            LF->>LF: dispose()
        else tidak cocok
            UC-->>LF: null
            LF-->>U: dialog error + clear field
        end
    end
```

> Catatan keamanan: password dibandingkan plaintext (tanpa hashing); kredensial DB di-hardcode di `DatabaseConnection`. Query memakai `PreparedStatement` sehingga aman dari SQL injection.

---

## 3. Sequence — Navigasi Antar Panel (CardLayout)

```mermaid
sequenceDiagram
    actor U as User
    participant MD as MainDashboardFrame
    participant CL as CardLayout (panelKontenUtama)
    participant P as Panel tujuan

    U->>MD: klik btnKelolaData (contoh)
    MD->>MD: setMenuColor(btnKelolaData)
    MD->>MD: lblJudulHalaman = "KELOLA DATA"
    MD->>CL: cl.show(panelKontenUtama, "panelKelolaData")
    CL->>P: tampilkan panel (sudah dibuat saat init)
    P-->>U: panel terlihat
```

> Kelima panel dibuat sekali di `initComponents()`, lalu hanya ditukar tampil via `CardLayout` — bukan dibuat ulang on-demand.

---

## 4. Sequence — CRUD Master Data (Sepatu)

CRUD dilakukan inline di `PanelKelolaData` (form di atas tabel), bukan lewat dialog popup. Supplier mengikuti pola yang sama.

```mermaid
sequenceDiagram
    actor U as User
    participant PK as PanelKelolaData
    participant SC as SepatuController
    participant DB as MySQL (sepatu)

    Note over PK: Saat panel dibuka
    PK->>SC: getAllSepatu() — SELECT * FROM sepatu
    SC-->>PK: List<Sepatu>
    PK->>PK: isi tableSepatu + TableDecorator.decorateTableSepatu()
    PK->>SC: generateIdSepatu()<br/>SELECT id_sepatu ... DESC LIMIT 1
    SC-->>PK: ID baru (mis. "SPT001"), set read-only

    alt Tambah
        U->>PK: klik btnTambahSepatu
        PK->>PK: validasi field + parse ukuran (stok dipaksa 0)
        PK->>SC: insertSepatu(s)<br/>INSERT INTO sepatu (...) VALUES (?,?,?,?,?)
        SC->>DB: execute
        SC-->>PK: true
        PK->>PK: clearFormSepatu() + loadTableSepatu()
    else Edit (pilih baris dulu)
        U->>PK: klik baris → tableSepatuMouseClicked (isi form, simpan selectedSepatuId)
        U->>PK: klik btnEditSepatu
        PK->>PK: validasi + ambil stokLama dari field read-only
        PK->>SC: updateSepatu(s)<br/>UPDATE sepatu SET nama,brand,ukuran,stok WHERE id_sepatu=?
        SC->>DB: execute
        SC-->>PK: true
        PK->>PK: clear + reload
    else Hapus
        U->>PK: klik btnDeleteSepatu
        PK->>U: konfirmasi "Yakin ingin menghapus?"
        U-->>PK: Ya
        PK->>SC: deleteSepatu(idSepatu)<br/>DELETE FROM sepatu WHERE id_sepatu=?
        SC->>DB: execute
        SC-->>PK: true
        PK->>PK: clear + reload
    end
```

> Setelah setiap operasi sukses, tabel selalu dimuat ulang penuh dari DB (`loadTableSepatu()`), bukan mutasi baris in-place. Stok sepatu tidak bisa diubah di sini — hanya lewat Stok Masuk.

---

## 5. Sequence — Stok Masuk (catat + update stok sepatu)

```mermaid
sequenceDiagram
    actor U as User
    participant PS as PanelStokMasuk
    participant DSup as DialogSupplier
    participant DSep as DialogSepatu
    participant SMC as StokMasukController
    participant DB as MySQL

    U->>PS: klik btnInputSupplier
    PS->>DSup: buka dialog (callback = this)
    U->>DSup: pilih supplier (double click)
    DSup->>PS: setSelectedSupplier(id, nama)

    U->>PS: klik btnInputSepatu
    PS->>DSep: buka dialog (callback = this)
    U->>DSep: pilih sepatu (double click)
    DSep->>PS: setSelectedSepatu(id, nama)

    U->>PS: isi jumlah + harga, klik btnTambahStok
    PS->>PS: validasi (supplier/sepatu dipilih, angka > 0)
    PS->>SMC: tambahStokMasuk(sm)

    rect rgb(235, 245, 255)
        Note over SMC,DB: Transaksi (autoCommit=false)
        SMC->>DB: INSERT INTO stok_masuk<br/>(id_supplier,id_sepatu,jumlah,harga_beli,total_harga)
        SMC->>DB: UPDATE sepatu SET stok = stok + ?<br/>WHERE id_sepatu = ?
        alt kedua sukses
            SMC->>DB: commit
            SMC-->>PS: true
        else error
            SMC->>DB: rollback
            SMC-->>PS: false
        end
    end

    PS->>PS: clearForm + loadTableStokMasuk()<br/>(SELECT JOIN supplier & sepatu, ORDER BY id_masuk DESC)
    PS-->>U: dialog "Stok berhasil ditambahkan!"
```

> Edit stok masuk menerapkan **selisih** jumlah ke `sepatu.stok`; hapus stok masuk **mengurangi** kembali stok. Semua dalam satu transaksi agar konsisten.

---

## 6. Ringkasan SQL per Controller

| Controller | Method | SQL |
|------------|--------|-----|
| UserController | `validasiLogin` | `SELECT * FROM user WHERE username=? AND password=?` |
| UserController | `getAllUsers` | `SELECT * FROM user ORDER BY id_user ASC` |
| UserController | `insertUser` | `INSERT INTO user (username,password,nama_lengkap,no_hp,role) VALUES (?,?,?,?,?)` |
| UserController | `updateUser` | `UPDATE user SET username=?,password=?,nama_lengkap=?,no_hp=?,role=? WHERE id_user=?` |
| UserController | `deleteUser` | `DELETE FROM user WHERE id_user=?` |
| SepatuController | `getAllSepatu` | `SELECT * FROM sepatu` |
| SepatuController | `insertSepatu` | `INSERT INTO sepatu (id_sepatu,nama_sepatu,brand,ukuran,stok) VALUES (?,?,?,?,?)` |
| SepatuController | `updateSepatu` | `UPDATE sepatu SET nama_sepatu=?,brand=?,ukuran=?,stok=? WHERE id_sepatu=?` |
| SepatuController | `deleteSepatu` | `DELETE FROM sepatu WHERE id_sepatu=?` |
| SepatuController | `generateIdSepatu` | `SELECT id_sepatu FROM sepatu ORDER BY id_sepatu DESC LIMIT 1` |
| SupplierController | `getAllSupplier` | `SELECT * FROM supplier ORDER BY id_supplier ASC` |
| SupplierController | `insertSupplier` | `INSERT INTO supplier (nama_supplier,no_telp,alamat) VALUES (?,?,?)` |
| SupplierController | `updateSupplier` | `UPDATE supplier SET nama_supplier=?,no_telp=?,alamat=? WHERE id_supplier=?` |
| SupplierController | `deleteSupplier` | `DELETE FROM supplier WHERE id_supplier=?` |
| StokMasukController | `tambahStokMasuk` | `INSERT INTO stok_masuk (...)` + `UPDATE sepatu SET stok=stok+? WHERE id_sepatu=?` (transaksi) |
| StokMasukController | `getAllStokMasuk` | `SELECT sm.*, s.nama_supplier, sp.nama_sepatu FROM stok_masuk sm LEFT JOIN supplier s ... LEFT JOIN sepatu sp ... ORDER BY sm.id_masuk DESC` |
| StokMasukController | `editStokMasuk` | `UPDATE stok_masuk SET ...` + `UPDATE sepatu SET stok=stok+? (selisih)` (transaksi) |
| StokMasukController | `deleteStokMasuk` | `DELETE FROM stok_masuk ...` + `UPDATE sepatu SET stok=stok-?` (transaksi) |

---

## 7. Catatan Teknis & Temuan

- **Role-based access**: `MainDashboardFrame.setHakAkses()` menyembunyikan menu sesuai role; `PanelKelolaData.setHakAksesPanel()` menyembunyikan tab (Super Admin → kelola Pegawai; Admin Gudang → kelola Sepatu + Supplier).
- **Stok hanya berubah lewat Stok Masuk** — di Kelola Data field stok read-only dan dipaksa 0 saat insert.
- **Transaksi** dipakai di semua operasi `StokMasukController` agar `stok_masuk` dan `sepatu.stok` selalu sinkron.
- **Bug guard mati** di `PanelKelolaData` (Edit/Delete Sepatu): cek `selectedSepatuId == null`, padahal field hanya pernah `""` atau ID valid — guard tak pernah aktif.
- **`getKoneksi()` bukan singleton** — tiap panggilan membuat `Connection` baru dan menimpa field static; aman untuk app single-user tapi rapuh.
- **`DialogSepatu`/`DialogSupplier`** adalah picker read-only (filter di sisi Java), hanya dipakai oleh Stok Masuk — bukan dialog CRUD master data.
