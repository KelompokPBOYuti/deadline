/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import myBank.dao.PegawaiDao;
import myBank.dao.TabunganDao;
import myBank.database.connectionDatabase;
import myBank.model.PegawaiEntity;
import myBank.view.manager.pegawai.PegawaiNew;
import myBank.view.manager.pegawai.pegawaiEdit;

/**
 *
 * @author Fauzi
 */
public class pegawaiController {

    Date date = new Date();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String tglSekarang = formatter.format(date);
    connectionDatabase conn = new connectionDatabase();
    PegawaiDao pegawaiDao = new PegawaiDao();

    public List<PegawaiEntity> loadDataPegawai() {
        return pegawaiDao.selectAllPegawai();
    }

    public String movePhoto(String pathPhoto, String namaPhoto) {
        String result = "null";
        File pathiOld = new File(pathPhoto);
        File pathNew = new File("src/mybank/resource/photo/pegawai/" + namaPhoto);
        try {
            pathNew.delete();
            pathiOld.renameTo(pathNew);
            result = namaPhoto;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Photo gagal di salin. " + e.getMessage());
            result = "null";
        }
        return result;
    }

    public boolean RemovePhoto(String pathPhoto) {
        boolean result = false;
        File pathRemove = new File(pathPhoto);
        try {
            result = pathRemove.delete();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Photo gagal di hapus. " + e.getMessage());
            result = false;
        }
        return result;
    }

    private String generateIdPegawai(String tgllahir) {
        String id;
        tgllahir = tgllahir.replace("-", "");
        tgllahir = tgllahir.substring(2, tgllahir.length());
        id = pegawaiDao.noUrutPegawai() + tgllahir;
        return id;
    }

    private String generatePassword() {
        Random random = new Random();
        String pass = "";
        for (int i = 0; i < 8; i++) {
            pass += String.valueOf(random.nextInt(10));
        }
        return pass;
    }
/// add data

    public void addPegawai(PegawaiNew view) {
        String noKtp = view.getNoKtp().getText();
        String nama = view.getNama().getText();
        String jenisKelamin = "";
        if (view.getJkLakiLaki().isSelected()) {
            jenisKelamin = "Laki - laki";
        } else {
            jenisKelamin = "Perempuan";
        }
        String tempatLahir = view.getTempatLahir().getText();
        String tglLahir = view.getTglLahir();
        String alamat = view.getAlamat().getText();
        String noTlp = view.getNoTlp().getText();
        String Jabatan = view.getJabatan().getSelectedItem().toString();
        String pathPhoto = view.getPahtPhoto();
        if (noKtp.equals("") || nama.equals("") || jenisKelamin.equals("") || tempatLahir.equals("") || tglLahir.equals("") || alamat.equals("") || noTlp.equals("") || Jabatan.equals("")) {
            view.setValidasi("Data pegawai belum lengkap", true);
        } else if (true == pegawaiDao.cekNoKTP(noKtp)) {
            view.setValidasi("No KTP sudah digunakan", true);
        } else {
            String idPegawai = "";
            if (Jabatan.equals("TELLER")) {
                idPegawai = "D" + generateIdPegawai(tglLahir);
            }
            if (Jabatan.equals("CS")) {
                idPegawai = "C" + generateIdPegawai(tglLahir);
            }
            if (Jabatan.equals("MANAGER")) {
                idPegawai = "A" + generateIdPegawai(tglLahir);
            }
            PegawaiEntity pegawai = new PegawaiEntity();
            pegawai.setIdPegawai(idPegawai);
            pegawai.setNoKtp(noKtp);
            pegawai.setNama(nama);
            pegawai.setNoTlp(noTlp);
            pegawai.setJenisKelamin(jenisKelamin);
            pegawai.setTempatLahir(tempatLahir);
            pegawai.setTanggalLahir(java.sql.Date.valueOf(tglLahir));
            pegawai.setJabatan(Jabatan);
            pegawai.setAlamat(alamat);
            pegawai.setPassword(generatePassword());
            String namaPhoto = "null";
            if (pathPhoto.equals("null")) {
                namaPhoto = "null";
            } else {
                namaPhoto = movePhoto(pathPhoto, idPegawai + ".png");
            }
            pegawai.setPhoto(namaPhoto);
            pegawai.setTglDaftar(java.sql.Date.valueOf(tglSekarang));
            pegawai.setTglUpdate(java.sql.Date.valueOf(tglSekarang));
            if (pegawaiDao.insertPegawai(pegawai) == true) {
                JOptionPane.showMessageDialog(null, "Data pegawai berhasil disimpan");
                view.refreshPegawaiTable();
            } else {
                JOptionPane.showMessageDialog(null, "Data pegawai gagal disimpan");
            }
        }

    }
/// edit data

    public PegawaiEntity loadEditPegawai(String idPegawai) {
        return pegawaiDao.selectPegawaiEdit(idPegawai);
    }

    public void editPegawai(pegawaiEdit view) {
        String idPegawai = view.getIdPegawai();
        String noKtp = view.getNoKtp().getText();
        String noKtpRestore = view.getNoKtpRestore();
        String nama = view.getNama().getText();
        String jenisKelamin = "";
        if (view.getJkLakiLaki().isSelected()) {
            jenisKelamin = "Laki - laki";
        } else {
            jenisKelamin = "Perempuan";
        }
        String tempatLahir = view.getTempatLahir().getText();
        String tglLahir = view.getTglLahir();
        String alamat = view.getAlamat().getText();
        String noTlp = view.getNoTlp().getText();
        String Jabatan = view.getJabatan().getSelectedItem().toString();
        String pathPhoto = view.getPahtPhoto();
        String pathRestorePhoto = view.getPahtRestorePhoto();
        if (noKtp.equals("") || nama.equals("") || jenisKelamin.equals("") || tempatLahir.equals("") || tglLahir.equals("") || alamat.equals("") || noTlp.equals("") || Jabatan.equals("")) {
            view.setValidasi("Data pegawai belum lengkap", true);
        } else {
            PegawaiEntity pegawai = new PegawaiEntity();
            pegawai.setIdPegawai(idPegawai);
            pegawai.setNama(nama);
            pegawai.setNoTlp(noTlp);
            pegawai.setJenisKelamin(jenisKelamin);
            pegawai.setTempatLahir(tempatLahir);
            pegawai.setTanggalLahir(java.sql.Date.valueOf(tglLahir));
            pegawai.setJabatan(Jabatan);
            pegawai.setAlamat(alamat);
            pegawai.setPassword(generatePassword());
            pegawai.setTglUpdate(java.sql.Date.valueOf(tglSekarang));

            String namaPhoto = "null";
            if (pathPhoto.equals("null")) {
                namaPhoto = "null";
                RemovePhoto(pathRestorePhoto);
            } else if (!pathPhoto.equals("null")) {
                if (pathPhoto.equals(pathRestorePhoto)) {
                    namaPhoto = idPegawai + ".png";
                } else {
                    namaPhoto = movePhoto(pathPhoto, idPegawai + ".png");
                }
            } else if (pathRestorePhoto.equals("null")) {
                namaPhoto = "null";
            } else {
                namaPhoto = "null";
            }
            pegawai.setPhoto(namaPhoto);
            if (!noKtp.equals(noKtpRestore)) {
                if (pegawaiDao.cekNoKTP(noKtp) == true) {
                    view.setValidasi("No KTP sudah digunakan", true);
                } else {
                    pegawai.setNoKtp(noKtp);
                    if (pegawaiDao.updatePegawai(pegawai) == true) {
                        JOptionPane.showMessageDialog(null, "Data pegawai berhasil diedit");
                        view.refreshPegawaiTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Data pegawai gagal diedit");
                    }
                }
            } else {
                pegawai.setNoKtp(noKtp);
                if (pegawaiDao.updatePegawai(pegawai) == true) {
                    JOptionPane.showMessageDialog(null, "Data pegawai berhasil diedit");
                    view.refreshPegawaiTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Data pegawai gagal diedit");
                }
            }
        }
    }
}
