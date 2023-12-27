package Game;

import javax.swing.*;

public class SinglePlayerGame extends JFrame {


    public SinglePlayerGame(){
        this.setTitle("单人模式");
        this.setVisible(true);
        this.setSize(768,576);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
}
