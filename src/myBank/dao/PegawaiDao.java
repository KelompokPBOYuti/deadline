/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import myBank.database.connectionDatabase;
import myBank.model.PegawaiEntity;

/**
 *
 * @author Fauzi
 */
public class PegawaiDao {

    connectionDatabase conn = new connectionDatabase();

    public List<PegawaiEntity> selectAllPegawai() {
        List<PegawaiEntity> listNasabah = new ArrayList<>();
        String selectSQL = "SELECT id_pgw,no_ktp_pgw,nama_pgw,jk_pgw,jabatan FROM tbpegawai";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(selectSQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PegawaiEntity pegawai = new PegawaiEntity();
                pegawai.setIdPegawai(rs.getString("id_pgw"));
                pegawai.setNoKtp(rs.getString("no_ktp_pgw"));
                pegawai.setNama(rs.getString("nama_pgw"));
                pegawai.setJenisKelamin(rs.getString("jk_pgw"));
                pegawai.setJabatan(rs.getString("jabatan"));
                listNasabah.add(pegawai);
            }
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil seluruh data nasaabah : " + ex.getMessage());
        } finally {
            conn.closeConnection();
        }
        return listNasabah;
    }

    public boolean cekNoKTP(String noKTP) {
        boolean result = false;
        String noKtpSQL;
        noKtpSQL = "SELECT no_ktp_pgw FROM tbpegawai WHERE no_ktp_pgw= ? ";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(noKtpSQL);
            statement.setString(1, noKTP);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException ex) {
            System.out.println("Terjadi erorr" + ex.getMessage());
        } finally {
            conn.closeConnection();
        }
        return result;
    }

    public String noUrutPegawai() {
        int noUrut = 0;
        String noUrutSQL;
        noUrutSQL = "SELECT COUNT(*) FROM tbpegawai";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(noUrutSQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                noUrut = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("Terjadi erorr" + ex.getMessage());
        } finally {
            conn.closeConnection();
        }
        return String.valueOf(noUrut);
    }

    public boolean insertPegawai(PegawaiEntity pegawai) {
        boolean result = false;
        String insertSQL = "INSERT INTO tbpegawai(id_pgw, no_ktp_pgw, nama_pgw, tlp_pgw, jk_pgw, tempat_lahir, tanggal_lahir, jabatan, alamat, password, photo, tgl_daftar, tgl_update) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(insertSQL);
            statement.setString(1, pegawai.getIdPegawai());
            statement.setString(2, pegawai.getNoKtp());
            statement.setString(3, pegawai.getNama());
            statement.setString(4, pegawai.getNoTlp());
            statement.setString(5, pegawai.getJenisKelamin());
            statement.setString(6, pegawai.getTempatLahir());
            statement.setDate(7, pegawai.getTanggalLahir());
            statement.setString(8, pegawai.getJabatan());
            statement.setString(9, pegawai.getAlamat());
            statement.setString(10, pegawai.getPassword());
            statement.setString(11, pegawai.getPhoto());
            statement.setDate(12, pegawai.getTglDaftar());
            statement.setDate(13, pegawai.getTglUpdate());
            statement.executeUpdate();
            statement.close();
            result = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saat menyimpan data pegawai ke database : " + ex.getMessage());
            result = false;
        } finally {
            conn.closeConnection();
        }
        return result;
    }

    //edit
    public PegawaiEntity selectPegawaiEdit(String idPegawai) {
        PegawaiEntity pegawai = new PegawaiEntity();
        String selectSQL = "SELECT  id_pgw,no_ktp_pgw,nama_pgw,tlp_pgw,jk_pgw,tempat_lahir,tanggal_lahir,jabatan,alamat,photo FROM tbpegawai WHERE id_pgw =?";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(selectSQL);
            statement.setString(1, idPegawai);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                pegawai.setIdPegawai(rs.getString("id_pgw"));
                pegawai.setNoKtp(rs.getString("no_ktp_pgw"));
                pegawai.setNama(rs.getString("nama_pgw"));
                pegawai.setJenisKelamin(rs.getString("jk_pgw"));
                pegawai.setTempatLahir(rs.getString("tempat_lahir"));
                pegawai.setTanggalLahir(rs.getDate("tanggal_lahir"));
                pegawai.setAlamat(rs.getString("alamat"));
                pegawai.setJabatan(rs.getString("jabatan"));
                pegawai.setNoTlp(rs.getString("tlp_pgw"));
                pegawai.setPhoto(rs.getString("photo"));
            }
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi Erorr saat select data pegawai edit " + e.getMessage());
        } finally {
            conn.closeConnection();
        }
        return pegawai;
    }

    public boolean updatePegawai(PegawaiEntity pegawai) {
        boolean result = false;
        String insertSQL = "UPDATE tbpegawai SET no_ktp_pgw=? ,nama_pgw= ? ,tlp_pgw= ? ,jk_pgw = ? , tempat_lahir = ? , tanggal_lahir =? ,jabatan = ? , alamat = ? ,photo =?,tgl_update = ? WHERE id_pgw = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(insertSQL);            
            statement.setString(1, pegawai.getNoKtp());
            statement.setString(2, pegawai.getNama());
            statement.setString(3, pegawai.getNoTlp());
            statement.setString(4, pegawai.getJenisKelamin());
            statement.setString(5, pegawai.getTempatLahir());
            statement.setDate(6, pegawai.getTanggalLahir());
            statement.setString(7, pegawai.getJabatan());
            statement.setString(8, pegawai.getAlamat());            
            statement.setString(9, pegawai.getPhoto());            
            statement.setDate(10, pegawai.getTglUpdate());
            statement.setString(11, pegawai.getIdPegawai());
            statement.executeUpdate();
            statement.close();
            result = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saat update data pegawai ke database : " + ex.getMessage());
            result = false;
        } finally {
            conn.closeConnection();
        }
        return result;
    }
}
