/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.controller;

import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import myBank.dao.transaksiDao;
import myBank.database.connectionDatabase;
import myBank.model.TabunganEntity;
import myBank.view.teller.kredit.kreditPanel;

/**
 *
 * @author Yoshino
 */
public class TransaksiController {

    transaksiDao transaksiDao = new transaksiDao();

    public void getDataKredit(kreditPanel view) {
        String noRek = view.getNorek().getText();
       
        if (noRek.equals("")) {
            JOptionPane.showMessageDialog(null, "Tentukan nomor rekening");
        } else {
            TabunganEntity  tabungan = transaksiDao.selectKredit(noRek);            
            view.setNamaNasabah(tabungan.getNama());
            view.setSaldoNasabah(String.valueOf(tabungan.getSaldo()));
        }
    }
}
