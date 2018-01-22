/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import myBank.database.connectionDatabase;
import myBank.model.TabunganEntity;

/**
 *
 * @author Fauzi
 */
public class TabunganDao {

    connectionDatabase conn = new connectionDatabase();

    public TabunganEntity selectNasabahBaru(String idNasabah) {
        TabunganEntity nasabah = new TabunganEntity();
        String selectSQL = "SELECT tbtabungan.no_rek,tbnasabah.no_ktp,tbnasabah.nama_ns,tbnasabah.jk_ns,tbnasabah.tempat_lahir_ns,tbnasabah.tanggal_lahir_ns,tbnasabah.alamat_ns,tbtabungan.saldo,tbnasabah.id_ns,tbnasabah.password,tbnasabah.tlp_ns FROM tbnasabah INNER JOIN tbtabungan ON tbnasabah.id_ns = tbtabungan.id_ns WHERE tbnasabah.id_ns=?";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(selectSQL);
            statement.setString(1, idNasabah);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                nasabah.setNo_rek(rs.getString("no_rek"));
                nasabah.setNoKtp(rs.getString("no_ktp"));
                nasabah.setNama(rs.getString("nama_ns"));
                nasabah.setJenisKelamin(rs.getString("jk_ns"));
                nasabah.setTempatLahir(rs.getString("tempat_lahir_ns"));
                nasabah.setTanggalLahir(rs.getDate("tanggal_lahir_ns"));
                nasabah.setAlamat(rs.getString("alamat_ns"));
                nasabah.setSaldo(rs.getDouble("saldo"));
                nasabah.setIdNasabah(rs.getString("id_ns"));
                nasabah.setPassword(rs.getString("password"));
                nasabah.setNoTlp(rs.getString("tlp_ns"));
            }
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi Erorr saat select data nasabah baru " + e.getMessage());
        } finally {
            conn.closeConnection();
        }
        return nasabah;
    }

    public boolean insertTabungan(TabunganEntity tabungan) {
        boolean result = false;
        String insertSQL = "INSERT INTO tbtabungan(no_rek,id_ns,saldo) VALUES (?,?,?)";
        try {
            PreparedStatement statement = conn.openConnection().prepareStatement(insertSQL);
            statement.setString(1, tabungan.getNo_rek());
            statement.setString(2, tabungan.getIdNasabah());
            statement.setDouble(3, tabungan.getSaldo());
            statement.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saat menyimpan data tabungan ke database : " + ex.getMessage());
            result = false;
        } finally {
            conn.closeConnection();
        }
        return result;
    }

    public double cekSaldoNasabah(String idNasabah) {
        String saldoSQL = "SELECT saldo FROM tbtabungan WHERE id_ns=?";
        double saldo;
        saldo = 0;
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(saldoSQL);
            statement.setString(1, idNasabah);
            statement.setString(1, idNasabah);
            ResultSet rs = statement.executeQuery();
            rs.next();
            saldo = rs.getDouble("saldo");
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saat cek saldo nasabah : " + ex.getMessage());
        } finally {
            conn.closeConnection();
        }
        return saldo;
    }

    public boolean deleteTabungan(String idNasabah) {
        boolean result = false;
        String deleteSQL = "DELETE FROM tbtabungan WHERE id_ns = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(deleteSQL);
            statement.setString(1, idNasabah);
            statement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data tabungan gagal dihapus "+ e.getMessage());
            result = false;
        }
        return result;
    }
}
