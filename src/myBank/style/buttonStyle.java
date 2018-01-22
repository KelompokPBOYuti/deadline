/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.style;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author Fauzi
 */
public class buttonStyle {

    private final Border borderMoved = javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255));
    ;
   private final Color backgroundMoved = new Color(35, 171, 226);
   private final Color backgroundExited = new Color(32, 103, 178);
   private final Color backgroundPressed = new Color(255, 255, 255);
   private final Color backgroundReleased = new Color(35, 171, 226);

    public void borderMouseExit(JLabel label) {
        label.setBorder(null);
    }

    public void borderMouseMoved(JLabel label) {
        label.setBorder(borderMoved);
    }

    public void backgroundMouseExit(JLabel label) {
        label.setBackground(backgroundExited);
    }
    public void backgroundMouseMoved(JLabel label) {
        label.setBackground(backgroundMoved);
    }
    public void backgroundMousePressed(JLabel label){
        label.setBackground(backgroundPressed);
    }
    public void backgroundMouseReleased(JLabel label){
        label.setBackground(backgroundReleased);
    }
    public void backgroundMouseExit(JPanel panel) {
        panel.setBackground(backgroundExited);
    }
    public void backgroundMouseMoved(JPanel panel) {
        panel.setBackground(backgroundMoved);
    }
    public void backgroundMousePressed(JPanel panel){
        panel.setBackground(backgroundPressed);
    }
    public void backgroundMouseReleased(JPanel panel){
        panel.setBackground(backgroundReleased);
    }
}
