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
import myBank.model.UserEntity;

/**
 *
 * @author Fauzi
 */
public class loginDao {

    UserEntity listUser = new UserEntity();
    connectionDatabase conn = new connectionDatabase();

    public UserEntity loadUser() {
        return listUser;
    }

    public boolean cekLogin(String idUser, String password) {
        boolean result = false;
        String loginSQL;
        String ketrangan;
        loginSQL = "SELECT id_pgw,nama_pgw,jabatan,photo FROM tbpegawai WHERE id_pgw = ? AND password = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.openConnection().prepareStatement(loginSQL);
            statement.setString(1, idUser);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                listUser.setId_user(rs.getString("id_pgw"));
                listUser.setNama(rs.getString("nama_pgw"));
                listUser.setjabatan(rs.getString("jabatan"));
                listUser.setPhoto(rs.getString("photo"));
                result = true;
            } else {
                result = false;
            }
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi Erorr saat login " + e.getMessage());
        } finally {
            conn.closeConnection();
        }
        return result;
    }
}
