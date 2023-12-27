package Game;

import javax.swing.*;

public class DoublePlayerGame extends JFrame {
    
    private static JFrame jFrame=new JFrame();
    public DoublePlayerGame(){
        jFrame.setTitle("双人模式");
        jFrame.setVisible(true);
        jFrame.setSize(768,576);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
    }
    
    public static JFrame getInstance(){
        return jFrame;
    }
}
