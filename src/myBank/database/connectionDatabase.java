/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import myBank.view.message.shortMessage;

/**
 *
 * @author Fauzi
 */
public class connectionDatabase {

    private Connection conn;
    final private String db = "dbmybank";
    final private String user = "root";
    final private String pass = "";
    final private String url = "jdbc:mysql://localhost:3306/" + db;

    public Connection openConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                shortMessage message = new shortMessage(null, true);
                message.setMesage("Gagal koneksi", "Program gagal koneksi ke database");
                message.setVisible(true);
            }
        }
        return conn;
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                shortMessage message = new shortMessage(null, true);
                message.setMesage("Gagal koneksi", "Program gagal tutup koneksi <br> ke database");
                message.setVisible(true);                
            }
        }
    }
}
