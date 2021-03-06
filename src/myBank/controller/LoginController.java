/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.controller;

import javax.swing.JOptionPane;
import myBank.dao.loginDao;
import myBank.model.UserEntity;
import myBank.view.customerService.customerServiceFrame;
import myBank.view.login.loginFrame;
import myBank.view.manager.ManagerFrame;
import myBank.view.teller.tellerFrame;

/**
 *
 * @author Fauzi
 */
public class LoginController {

    loginDao dao = new loginDao();

    public void login(loginFrame view) {
        String idUser = view.getIdUser().getText();
        String pass = String.valueOf(view.getPassword().getText());
        if (!idUser.equals("") || !pass.equals("")) {
            if (idUser.equals("")) {
                view.setMessageValidasi("Isi ID user", true);
            } else if (pass.equals("")) {
                view.setMessageValidasi("Isi Password user", true);
            } else {
                if (dao.cekLogin(idUser, pass) == true) {
                    UserEntity user = dao.loadUser();
                    switch (user.getjabatan()) {
                        case "TELLER":
                            new tellerFrame(user).setVisible(true);                            
                            view.dispose();
                            break;
                        case "MANAGER":
                            new ManagerFrame(user).setVisible(true);
                            break;
                        case "CS":
                            customerServiceFrame cs = new customerServiceFrame();
                            cs.setVisible(true);
                            view.dispose();
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "terjadi kesalahan");
                    }

                } else {
                    view.setMessageValidasi("Gagal login. Cek ID atau Password", true);
                }
            }

        }
    }
}
