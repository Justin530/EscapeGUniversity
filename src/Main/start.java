package Main;

import MapEditor.Editor.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class start extends JFrame {

    private JFrame start;

    public start(){
        start=new JFrame("Start");
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setSize(768,576);
        start.setResizable(false);
        start.setVisible(true);
        start.setLocationRelativeTo(null);




        ImageIcon backgound = new ImageIcon("src/res/images/bg.png");
        backgound.setImage(backgound.getImage().getScaledInstance(768,576, Image.SCALE_DEFAULT));
        JLabel jLabel=new JLabel(backgound);
        jLabel.setBounds(0,0,768,576);

        JPanel imagePanel;
        imagePanel =(JPanel)start.getContentPane();
        imagePanel.setOpaque(false);
        imagePanel.setLayout(null);




        JButton jButton=new JButton();
        jButton.setText("进入G大");
        jButton.setBounds(334,330,110,40);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setVisible(false);
                Game game=new Game();
            }
        });

        imagePanel.add(jButton);


        JButton jButton1=new JButton();
        jButton1.setText("设置");
        jButton1.setBounds(10,10,110,40);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        imagePanel.add(jButton1);

        JButton jButton2=new JButton();
        jButton2.setText("操作说明");
        jButton2.setBounds(10,50,110,40);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        imagePanel.add(jButton2);

        JButton jButton3=new JButton();
        jButton3.setText("自定义地图");
        jButton3.setBounds(10,90,110,40);
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window window=new window();
            }
        });
        imagePanel.add(jButton3);

        JButton jButton4=new JButton();
        jButton4.setText("退出游戏");
        jButton4.setBounds(10,130,110,40);
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        imagePanel.add(jButton4);




        start.getLayeredPane().setLayout(null);
        start.getLayeredPane().add(jLabel,new Integer(Integer.MIN_VALUE));




    }





    public JFrame getStart() {
        return start;
    }
}
