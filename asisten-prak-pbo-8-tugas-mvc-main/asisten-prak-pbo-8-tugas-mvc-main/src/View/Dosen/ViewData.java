package View.Dosen;

import Controller.ControllerDosen;
import Model.Dosen.ModelDosen;
import View.Home;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewData extends JFrame {
    /* 
      Membuat variabel "baris" untuk menyimpan baris ke berapa yang dipilih saat 
      user memilih salah satu data yang ada di tabel 
     */
    Integer baris;
    
    ControllerDosen controller;

    // Menginisiasi komponen
    JLabel header = new JLabel("Data Dosen");
    JButton tombolTambah = new JButton("Tambah Dosen");
    JButton tombolEdit = new JButton("Edit Dosen");
    JButton tombolHapus = new JButton("Hapus Dosen");
    JButton tombolKembali = new JButton("Kembali ke menu utama");

    /*
      Untuk membuat Tabel, kita memerlukan 3 komponen, yaitu:
      1. JTable sebagai komponen tabelnya
      2. DefaultTableModel untuk model atau isinya
      3. JScrollPane supaya tabel dapat di-scroll saat datanya melebihi ukuran layar.
     */
    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;

    /*
      Nama kolom tabelnya disimpan ke dalam variabel "namaKolom" yang memiliki 
      tipe data Array String.
     */
    String namaKolom[] = {"ID", "Nama", "NIDN"};

    public ViewData() {
        tableModel = new DefaultTableModel(namaKolom, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        setTitle("Daftar Dosen");
        setVisible(true);
        setSize(552, 580);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(header);
        add(scrollPane);
        add(tombolTambah);
        add(tombolEdit);
        add(tombolHapus);
        add(tombolKembali);

        header.setBounds(20, 8, 440, 24);
        scrollPane.setBounds(20, 36, 512, 320);
        tombolTambah.setBounds(20, 370, 512, 40);
        tombolEdit.setBounds(20, 414, 512, 40);
        tombolHapus.setBounds(20, 458, 512, 40);
        tombolKembali.setBounds(20, 502, 512, 40);

        /*
          Memanggil method showData() dari controller untuk
          mengisi tabel dengan data yang diambil dari DB
         */
        controller = new ControllerDosen(this);
        controller.showAllDosen();

        // Menambahkan event handling ketika salah satu baris di tabel dipilih
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // Mengambil baris ke-n dari tabel
                baris = table.getSelectedRow();
            }
        });

        // Memberikan event handling ketika tombol "Tambah Dosen" diklik
        tombolTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika tombol tambah diklik, maka program akan berpindah ke halaman InputData()
                dispose();
                new InputData();
            }
        });

        // Memberikan event handling ketika tombol "Edit Dosen" diklik
        tombolEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengecek apakah ada baris di dalam tabel yang dipilih atau tidak
                if (baris != null) {
                    /*
                      Membuat instance "dosen" yang digunakan untuk menyimpan 
                      informasi dosen yang diklik di table.
                    */
                    ModelDosen dosenTerpilih = new ModelDosen();
                    
                    // Mengambil id dan nama berdasarkan baris yang dipilih
                    Integer id = (int) table.getValueAt(baris, 0);
                    String nama = table.getValueAt(baris, 1).toString();
                    String nidn = table.getValueAt(baris, 2).toString();
                    
                    // Menyimpan informasi id, nama, dan nidn ke objek "dosenTerpilih".
                    dosenTerpilih.setId(id);
                    dosenTerpilih.setNama(nama);
                    dosenTerpilih.setNidn(nidn);

                    /* 
                      Ketika tombol edit diklik, maka program akan berpindah ke 
                      halaman EditData() dengan membawa id, nama, dan nidn untuk
                      diberikan ke halaman EditData()
                     */
                    dispose();
                    new EditData(dosenTerpilih);
                } else {
                    JOptionPane.showMessageDialog(null, "Data belum dipilih.");
                }
            }
        });

        // Memberikan event handling ketika tombol "Hapus Dosen" diklik
        tombolHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengecek apakah ada baris di dalam tabel yang dipilih atau tidak
                if (baris != null) {
                    controller.deleteDosen(baris);
                    
                    /*
                      Mengembalikan nilai dari variabel baris ke null. Kenapa?
                      Karena halaman tidak dimuat ulang, hanya tabel yang direfresh.
                      Sehingga variabel baris harus dikembalikan ke value awalnya.
                      
                      Jika tidak, maka setelah user menghapus dosen,
                      lalu langsung menekan tombol "Edit" atau "Hapus", maka akan 
                      terjadi error karena variabel baris masih menyimpan nilai lama,
                      sedangkan baris yang lama sudah terhapus.
                    */
                    baris = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Data belum dipilih.");
                }
            }
        });
        
        tombolKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Home();
            }
        });
    }

    public JTable getTableDosen() {
        return table;
    }
}
