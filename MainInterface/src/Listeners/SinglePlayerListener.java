package Listeners;

import Game.SinglePlayerGame;
import window.window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayerListener  implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("进入单人模式");
        window.getInstance().setVisible(false);

        new SinglePlayerGame();
    }


}
