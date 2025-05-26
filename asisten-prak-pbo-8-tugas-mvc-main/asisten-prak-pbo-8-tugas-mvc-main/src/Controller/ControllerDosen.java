package Controller;

import Model.Dosen.*;
import View.Dosen.*;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerDosen {

    ViewData halamanTable;
    InputData halamanInput;
    EditData halamanEdit;

    InterfaceDAODosen daoDosen;

    // Membuat variabel "daftarDosen" untuk menyimpan data dosen yg diambil dari DB.
    List<ModelDosen> daftarDosen;

    /*
      Kalo temen-temen liat di sini, ada 3 fungsi contructor yang masing-masing memiliki
      parameter yang berbeda. Nah, hal ini disebut dengan "function overloading",
      yaitu sebuah fungsi yang memiliki nama sama tetapi memiliki parameter yang berbeda.
      
      Mengapa kita butuh "function overloading"?
      Karena dalam hal ini, controller dosen akan digunakan pada 3 halaman atau 3 view yang berbeda, 
      yaitu Halaman Table, Halaman Input, dan Halaman Edit.
     */
    public ControllerDosen(ViewData halamanTable) {
        this.halamanTable = halamanTable;
        this.daoDosen = new DAODosen();
    }

    public ControllerDosen(InputData halamanInput) {
        this.halamanInput = halamanInput;
        this.daoDosen = new DAODosen();
    }

    public ControllerDosen(EditData halamanEdit) {
        this.halamanEdit = halamanEdit;
        this.daoDosen = new DAODosen();
    }

    public void showAllDosen() {
        /*
          Mengambil daftar dosen dari database, 
          kemudian disimpan ke dalam variabel bernama list.
         */
        daftarDosen = daoDosen.getAll();

        /* 
          Supaya daftarDosen dapat dimasukkan ke dalam tabel, kita perlu 
          mengubah daftarDosen yang memiliki tipe data List menjadi 
          tipe data Array Object. Jika pada pertemuan kemarin kita melakukannya
          secara manual menggunakan looping, kali ini kita tidak perlu melakukan hal tersebut,
          karena class ModelTable sudah otomatis mengubahnya menjadi tipe data yang sesuai.
          
          Caranya kita hanya perlu membuat sebuah instance dari class ModelTable,
          kemudian kita masukkan variabel daftarDosen sebagai parameter constructor
          supaya dapat diubah menjadi sebuah isi table yang dapat dimasukkan ke dalam tabel.
         */
        ModelTable table = new ModelTable(daftarDosen);

        // Mengisi tabel yang berada pada halaman Table Dosen
        halamanTable.getTableDosen().setModel(table);
    }

    public void insertDosen() {
        try {
            // Membuat "dosen baru" yang isinya masih kosong
            ModelDosen dosenBaru = new ModelDosen();

            /*
              Mengambil input nama dan nidn menggunakan getter yang telah dibuat di view
              Nilai dari input kemudian disimpan ke dalam variabel "nama" dan "nidn".
             */
            String nama = halamanInput.getInputNama();
            String nidn = halamanInput.getInputNIDN();

            /*
              Mengecek apakah input dari nama atau nidn kosong/tidak.
              Jika kosong, maka buatlah sebuah exception.
             */
            if ("".equals(nama) || "".equals(nidn)) {
                throw new Exception("Nama atau NIDN tidak boleh kosong!");
            }

            // Mengisi nama dan nidn dari "dosen baru" yang dibuat tadi.
            dosenBaru.setNama(nama);
            dosenBaru.setNidn(nidn);

            // Memasukkan "dosen baru" ke dalam database.
            daoDosen.insert(dosenBaru);

            // Menampilkan pop-up ketika berhasil mengedit data
            JOptionPane.showMessageDialog(null, "Dosen baru berhasil ditambahkan.");

            // Terakhir, program akan pindah ke halaman Table Dosen
            halamanInput.dispose();
            new ViewData();
        } catch (Exception e) {
            // Menampilkan pop-up ketika terjadi error
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void editDosen(int id) {
        try {
            /*
              Membuat instance "dosen yang mau diedit" buat 
              menyimpan informasi dosen yang mau diedit.
             */
            ModelDosen dosenYangMauDiedit = new ModelDosen();

            /*
              Mengambil input nama dan nidn menggunakan getter yang telah dibuat di view
              Nilai dari input kemudian disimpan ke dalam variabel "nama" dan "nidn".
             */
            String nama = halamanEdit.getInputNama();
            String nidn = halamanEdit.getInputNIDN();

            /*
              Mengecek apakah input dari nama atau nidn kosong/tidak.
              Jika kosong, maka buatlah sebuah exception.
             */
            if ("".equals(nama) || "".equals(nidn)) {
                throw new Exception("Nama atau NIDN tidak boleh kosong!");
            }

            // Mengisi id, nama dan nidn dari "dosen baru" yang dibuat tadi.
            dosenYangMauDiedit.setId(id);
            dosenYangMauDiedit.setNama(nama);
            dosenYangMauDiedit.setNidn(nidn);

            // Memasukkan "dosen baru" ke dalam database.
            daoDosen.update(dosenYangMauDiedit);

            // Menampilkan pop-up ketika berhasil mengedit data
            JOptionPane.showMessageDialog(null, "Data dosen berhasil diubah.");

            // Terakhir, program akan pindah ke halaman Table Dosen
            halamanEdit.dispose();
            new ViewData();
        } catch (Exception e) {
            // Menampilkan pop-up ketika terjadi error
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void deleteDosen(Integer baris) {
        // Mengambil id dan nama berdasarkan baris yang dipilih
        Integer id = (int) halamanTable.getTableDosen().getValueAt(baris, 0);
        String nama = halamanTable.getTableDosen().getValueAt(baris, 1).toString();

        // Membuat Pop-Up untuk mengonfirmasi apakah ingin menghapus data
        int input = JOptionPane.showConfirmDialog(
                null,
                "Hapus " + nama + "?",
                "Hapus Dosen",
                JOptionPane.YES_NO_OPTION
        );

        // Jika user memilih opsi "yes", maka hapus data.
        if (input == 0) {
            /* 
              Memanggil method delete() untuk menghaous data dari DB
              berdasarkan id yang dipilih.
             */
            daoDosen.delete(id);

            // Menampilkan pop-up jika berhasil menghapus.
            JOptionPane.showMessageDialog(null, "Berhasil menghapus data.");

            // Memanggil method "showAllDosen()" untuk merefresh table.
            showAllDosen();
        }
    }
}
