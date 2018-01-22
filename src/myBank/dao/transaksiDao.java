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
 * @author Yoshino
 */
public class transaksiDao {
    connectionDatabase conn = new connectionDatabase();
    public TabunganEntity selectKredit(String noRek){
        String selectSql = "SELECT tbtabungan.no_rek,tbtabungan.id_ns,tbtabungan.saldo,tbnasabah.nama_ns FROM tbtabungan INNER JOIN tbnasabah ON tbtabungan.id_ns=tbnasabah.id_ns WHERE tbtabungan.no_rek=?";
        TabunganEntity tabungan = new TabunganEntity();
        PreparedStatement statement = null;        
        try {
            statement=conn.openConnection().prepareStatement(selectSql);
            statement.setString(1, noRek);
            ResultSet rs  = statement.executeQuery();
            if(rs.next()){
                tabungan.setNo_rek(rs.getString("no_rek"));
                tabungan.setIdNasabah(rs.getString("id_ns"));
                tabungan.setSaldo(rs.getDouble("saldo"));
                tabungan.setNama(rs.getString("nama_ns"));
                
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal seleksi di kredit" + e.getMessage());
        }
        return tabungan;
    }
    public TabunganEntity selectDebet(String noRek){
        String selectSql = "SELECT tbtabungan.no_rek,tbtabungan.id_ns,tbtabungan.saldo,tbnasabah.nama_ns FROM tbtabungan INNER JOIN tbnasabah ON tbtabungan.id_ns=tbnasabah.id_ns WHERE tbtabungan.no_rek=?";
        TabunganEntity tabungan = new TabunganEntity();
        PreparedStatement statement = null;        
        try {
            statement=conn.openConnection().prepareStatement(selectSql);
            statement.setString(1, noRek);
            ResultSet rs  = statement.executeQuery();
            if(rs.next()){
                tabungan.setNo_rek(rs.getString("no_rek"));
                tabungan.setIdNasabah(rs.getString("id_ns"));
                tabungan.setSaldo(rs.getDouble("saldo"));
                tabungan.setNama(rs.getString("nama_ns"));
                
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal seleksi di kredit" + e.getMessage());
        }
        return tabungan;
    }
}
