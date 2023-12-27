package window;

import javax.swing.*;
import java.awt.*;
import Listeners.*;

public class window extends JFrame {
    
    private static JFrame jFrame=new JFrame();
    public window() {



        jFrame.setTitle("EscapeGUniversity");
        jFrame.setSize(768, 576);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);


        JPanel imagePanel;
        ImageIcon background = new ImageIcon("src/images/mainbackground1.jpg");


        background.setImage(background.getImage().
                getScaledInstance(768, 576, Image.SCALE_DEFAULT));

        JLabel jLabel = new JLabel(background);

        jLabel.setBounds(0, 0, 768, 576);

        imagePanel = (JPanel) jFrame.getContentPane();
        imagePanel.setOpaque(false);

        imagePanel.setLayout(null);//绝对布局，每一个组件需要使用setBounds指定大小和位置。

        ImageIcon background1 = new ImageIcon("src/images/单人游戏.png");

        background1.setImage(background1.getImage().
                getScaledInstance(220, 60, Image.SCALE_DEFAULT));

        JButton jButton1 = new JButton();
        jButton1.setBounds(80,150,220,60);
        //jButton1.setBackground(Color.GRAY);
        jButton1.setIcon(background1);
        jButton1.addActionListener(new SinglePlayerListener());

        imagePanel.add(jButton1);


        ImageIcon background2 = new ImageIcon("src/images/双人游戏.png");

        background2.setImage(background2.getImage().getScaledInstance(220,60,Image.SCALE_DEFAULT));

        JButton jButton2 = new JButton();
        jButton2.setBounds(80,210,220,60);
        jButton2.setIcon(background2);
        jButton2.addActionListener(new DoublePlayerListener());
        imagePanel.add(jButton2);

        ImageIcon background3 = new ImageIcon("src/images/背景故事.png");

        background3.setImage(background3.getImage().getScaledInstance(220,60,Image.SCALE_DEFAULT));

        JButton jButton3 = new JButton();
        jButton3.setBounds(80,270,220,60);
        jButton3.setIcon(background3);
        jButton3.addActionListener(new BackStoryListener());
        imagePanel.add(jButton3);

        ImageIcon background4 = new ImageIcon("src/images/设置.png");

        background4.setImage(background4.getImage().getScaledInstance(220,60,Image.SCALE_DEFAULT));

        JButton jButton4 = new JButton();
        jButton4.setBounds(80,330,220,60);
        jButton4.setIcon(background4);
        jButton4.addActionListener(new SetupListener());
        imagePanel.add(jButton4);

        jFrame.getLayeredPane().setLayout(null);
        jFrame.getLayeredPane().add(jLabel,new Integer(Integer.MIN_VALUE));



    }
    
    public static JFrame getInstance(){
        return jFrame;
    }


}
