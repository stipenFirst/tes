package View;

import Controller.ControllerMahasiswa;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Home extends JFrame {
    // Membuat sebuah instance bernama controller dari class "ControllerMahasiswa".
    ControllerMahasiswa controller;
    
    JLabel header = new JLabel("Selamat Datang", SwingConstants.CENTER);
    JButton tombolMahasiswa = new JButton("Menu Mahasiswa");
    JButton tombolDosen = new JButton("Menu Dosen");

    public Home() {
        setTitle("Data Dosen dan Mahasiswa");
        setVisible(true);
        setSize(480, 240);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(header);
        add(tombolMahasiswa);
        add(tombolDosen);
        
        // Untuk memperbesar ukuran font
        header.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
        
        header.setBounds(20, 12, 440, 36);
        tombolDosen.setBounds(80, 56, 320, 40);
        tombolMahasiswa.setBounds(80, 100, 320, 40);

        /* 
          Memberikan event handling,
          Ketika tombol diklik, maka akan menuju ke halaman Mahasiswa atau Dosen.
         */
        tombolMahasiswa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new View.Mahasiswa.ViewData();
            }
        });
        tombolDosen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new View.Dosen.ViewData();
            }
        });
    }
}
