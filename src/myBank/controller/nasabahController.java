/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.controller;

import java.io.File;
import java.util.Date;
//import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import myBank.dao.TabunganDao;
import myBank.dao.nasabahDao;
import myBank.model.TabunganEntity;
import myBank.model.NasabahEntity;
import myBank.view.customerService.nasabah.NasabahEdit;
import myBank.view.customerService.nasabah.nasabahNew;
import myBank.view.customerService.nasabah.nasabahPanel;

/**
 *
 * @author Fauzi
 */
public class nasabahController {

    nasabahDao nasabahDao = new nasabahDao();
    TabunganDao tabunganDao = new TabunganDao();
    Date date = new Date();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String tglSekarang = formatter.format(date);

    public boolean movePhoto(String pathPhoto, String namaPhoto) {
        boolean result = false;
        File pathiOld = new File(pathPhoto);
        File pathNew = new File("src/mybank/resource/photo/" + namaPhoto);
        try {
            pathNew.delete();
            result = pathiOld.renameTo(pathNew);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Photo gagal di salin. " + e.getMessage());
            result = false;
        }
        return result;
    }

    public boolean RemovePhoto(String pathPhoto) {
        boolean result = false;
        File pathRemove = new File(pathPhoto);
        try {
            result = pathRemove.delete();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Photo gagal di salin. " + e.getMessage());
            result = false;
        }
        return result;
    }

    private String generateNoRek(String noKTP) {
        String rek = noKTP.substring(0, 6) + noKTP.substring(noKTP.length() - 4, noKTP.length()) + nasabahDao.noUrutNasabah();
        return rek;
    }

    private String generateIdNasabah(String tgl) {
        String id;
        tgl = tgl.replace("-", "");
        tgl = tgl.substring(2, tgl.length());
        id = "E" + nasabahDao.noUrutNasabah() + tgl;
        return id;
    }

    private String generatePassword() {
        Random random = new Random();
        String pass = "";
        for (int i = 0; i < 6; i++) {
            pass += String.valueOf(random.nextInt(10));
        }
        return pass;
    }

    public List<NasabahEntity> loadData() {
        return nasabahDao.selectNasabah();
    }

    public void addNasabah(nasabahNew view) {
        String message = "";
        String namaPhoto = "";
        String noKtp = view.getNoKTP().getText();
        String nama = view.getNama().getText();
        String jenisKelamin;
        if (view.getJkLaki().isSelected() == true) {
            jenisKelamin = "Laki - laki";
        } else {
            jenisKelamin = "Perempuan";
        }
        String tempatLahir = view.getTempatLahir().getText();
        String tanggalLahir = view.getTanggalLahir();
        String noTlp = view.getNoTlp().getText();
        String alamat = view.getAlamat().getText();
        String saldo = view.getSaldo().getText();
        String pathPhoto = view.getPathPhoto();
        if ("".equals(noKtp) || "".equals(nama) || "".equals(jenisKelamin) || "".equals(tempatLahir) || "".equals(tanggalLahir) || "".equals(noTlp) || "".equals(alamat)) {
            message = "* Data nasabah harus lengkap" + "<br>" + message;
            view.setValidasi(message, true);
        } else if (true == nasabahDao.cekNoKTP(noKtp)) {
            message = "* Maaf No KTP sudah digunakan";
            view.setValidasi(message, true);
        } else if (Integer.parseInt(saldo) < 100000) {
            message = "* Saldo awal minimal Rp 100.000";
            view.setValidasi(message, true);
        } else {
            String noRek = generateNoRek(noKtp);
            String idNasabah = generateIdNasabah(tanggalLahir);
            namaPhoto = idNasabah + ".png";
            String tglDaftar = tglSekarang;
            String tglUpdate = tglSekarang;
            TabunganEntity tabungan = new TabunganEntity();
            NasabahEntity nasabah = new NasabahEntity();
            nasabah.setIdNasabah(idNasabah);
            nasabah.setNoKtp(noKtp);
            nasabah.setNama(nama);
            nasabah.setJenisKelamin(jenisKelamin);
            nasabah.setTempatLahir(tempatLahir);
            nasabah.setTanggalLahir(java.sql.Date.valueOf(tanggalLahir));
            nasabah.setAlamat(alamat);
            nasabah.setNoTlp(noTlp);
            nasabah.setPassword(generatePassword());
            nasabah.setTglDaftar(java.sql.Date.valueOf(tglDaftar));
            nasabah.setTglUpdate(java.sql.Date.valueOf(tglUpdate));
            tabungan.setIdNasabah(idNasabah);
            tabungan.setNo_rek(noRek);
            tabungan.setSaldo(Double.valueOf(saldo));
            if (pathPhoto != "null") {
                movePhoto(pathPhoto, namaPhoto);
                nasabah.setPoto(namaPhoto);
            } else {
                nasabah.setPoto("null");
            }
            if ((nasabahDao.insertNasabah(nasabah) == true) && (tabunganDao.insertTabungan(tabungan) == true)) {
                JOptionPane.showMessageDialog(null, "Data nasabah berhasil di simpan");
                TabunganEntity dataNasabah = tabunganDao.selectNasabahBaru(idNasabah);
                view.setDataNasabah(dataNasabah.getNo_rek(), dataNasabah.getNoKtp(), dataNasabah.getNama(), dataNasabah.getJenisKelamin(), dataNasabah.getTempatLahir() + ", " + dataNasabah.getTanggalLahir(), dataNasabah.getAlamat(), dataNasabah.getSaldo(), dataNasabah.getIdNasabah(), dataNasabah.getPassword());
                view.setSlide2();
            } else {
                JOptionPane.showMessageDialog(null, "Data nasabah gagal di simpan");
            }
        }
    }
//edit data nasabah

    public void cekEditIdNasbah(String idNasabah) {
        if (idNasabah.equals("")) {
            JOptionPane.showMessageDialog(null, "Tentukan data nasabah yang akan di edit");
        } else {
            NasabahEntity dataNasabah = nasabahDao.selectNasabahEdit(idNasabah);
            new NasabahEdit(null, true, idNasabah, dataNasabah.getNoKtp(), dataNasabah.getNama(), dataNasabah.getJenisKelamin(), dataNasabah.getTempatLahir(), dataNasabah.getTanggalLahir(), dataNasabah.getAlamat(), dataNasabah.getNoTlp(), dataNasabah.getPoto()).show();
        }

    }

    public void editNasabah(NasabahEdit view) {
        String message = "";
        String namaPhoto = view.getNamaPhoto();
        String idNasabah = view.getIdNasabah();
        String noKtp = view.getNoKTP().getText();
        String noRestoreKtp = view.getRestoreNoKTP();
        String nama = view.getNama().getText();
        String jenisKelamin;
        if (view.getJkLaki().isSelected() == true) {
            jenisKelamin = "Laki - laki";
        } else {
            jenisKelamin = "Perempuan";
        }
        String tempatLahir = view.getTempatLahir().getText();
        String tanggalLahir = view.getTanggalLahir();
        String noTlp = view.getNoTlp().getText();
        String alamat = view.getAlamat().getText();
        String pathPhoto = view.getPathPhoto();
        String pathRestorePhoto = view.getPathRsetorePhoto();
        if ("".equals(noKtp) || "".equals(nama) || "".equals(jenisKelamin) || "".equals(tempatLahir) || "".equals(tanggalLahir) || "".equals(noTlp) || "".equals(alamat)) {
            message = "* Data nasabah harus lengkap";
            view.setValidasi(message, true);
        } else {
            NasabahEntity nasabah = new NasabahEntity();
            nasabah.setIdNasabah(idNasabah);
            nasabah.setNama(nama);
            nasabah.setJenisKelamin(jenisKelamin);
            nasabah.setTempatLahir(tempatLahir);
            nasabah.setTanggalLahir(java.sql.Date.valueOf(tanggalLahir));
            nasabah.setAlamat(alamat);
            nasabah.setNoTlp(noTlp);
            nasabah.setTglUpdate(java.sql.Date.valueOf(tglSekarang));
            namaPhoto = idNasabah + ".png";
            if (noKtp.equals(noRestoreKtp)) {
                nasabah.setNoKtp(noRestoreKtp);
                if (pathPhoto.equals(pathRestorePhoto)) {
                    nasabah.setPoto(namaPhoto);
                } else if (pathPhoto.equals("null")) {
                    nasabah.setPoto("null");
                    RemovePhoto(pathRestorePhoto);
                } else if (!pathPhoto.equals("null")) {
                    nasabah.setPoto(namaPhoto);
                    movePhoto(pathPhoto, namaPhoto);
                } else {
                    nasabah.setPoto("null");
                }
                if (nasabahDao.updateNasabah(nasabah) == true) {
                    JOptionPane.showMessageDialog(null, "Data nasabah berhasil di edit");
                    view.dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "Data nasabah gagal di edit");
                }
            } else {
                if (nasabahDao.cekNoKTP(noKtp) == true) {
                    message = "* No KTP sudah digunakan";
                    view.setValidasi(message, true);
                } else {
                    nasabah.setNoKtp(noKtp);
                    if (pathPhoto.equals(pathRestorePhoto)) {
                        nasabah.setPoto(namaPhoto);
                    } else if (pathPhoto.equals("null")) {
                        nasabah.setPoto("null");
                        RemovePhoto(pathRestorePhoto);
                    } else if (!pathPhoto.equals("null")) {
                        nasabah.setPoto(namaPhoto);
                        movePhoto(pathPhoto, namaPhoto);
                    } else {
                        nasabah.setPoto("null");
                    }
                    if (nasabahDao.updateNasabah(nasabah) == true) {
                        JOptionPane.showMessageDialog(null, "Data nasabah berhasil di edit");
                        view.dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "Data nasabah gagal di edit");
                    }
                }
            }

        }
    }

    //delete
    public void cekDeleteIdNasbah(String idNasabah) {
        if (idNasabah.equals("")) {
            JOptionPane.showMessageDialog(null, "Tentukan data nasabah yang akan di hapus");
        } else {
            if (tabunganDao.cekSaldoNasabah(idNasabah) > 0) {
                JOptionPane.showMessageDialog(null, "Data nasabah tidak bisa dihapus karena masih memiliki saldo ");
            } else {
                int Pilih = JOptionPane.showConfirmDialog(null, "Yakin data nasabah akan dihapus ?", "Hapus Nasabah", JOptionPane.YES_NO_OPTION);
                if (Pilih == JOptionPane.YES_OPTION) {
                    if ((tabunganDao.deleteTabungan(idNasabah) == true) && (nasabahDao.deleteNasabah(idNasabah) == true)){
                        JOptionPane.showMessageDialog(null, "Data nasabah berhasil dihapus");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Data nasabah gagal dihapus");
                    }
                } 
            }
        }
    }

    public List<NasabahEntity> cariDataNasabah(nasabahPanel view) {
        String keyWord = view.getCari().getText();
        List<NasabahEntity> res = new ArrayList<>();
        if (!"".equals(keyWord)) {
            res = nasabahDao.cariNasabah(keyWord);
        }
        return res;
    }
}
