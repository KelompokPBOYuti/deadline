/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myBank.style;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Fauzi
 */
public class layoutStyle {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double widthScreen = screenSize.getWidth();
    private final double heightScreen = screenSize.getHeight();
    private int xMouse;
    private int yMouse;
    private int locationX;
    private int locationY;

    public void setMouseLocation(int xMouse, int yMouse) {
        this.xMouse = xMouse;
        this.yMouse = yMouse;
    }
    

}
