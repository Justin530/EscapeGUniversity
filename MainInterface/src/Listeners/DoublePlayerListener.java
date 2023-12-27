package Listeners;

import Game.*;
import window.window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoublePlayerListener extends Frame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("进入双人模式");
        window.getInstance().setVisible(false);
        new DoublePlayerGame();
    }
}
