package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {



        JFrame start=new JFrame("Start");
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setSize(768,576);
        start.setResizable(false);
        start.setVisible(true);
        start.setLocationRelativeTo(null);

        ImageIcon backgound = new ImageIcon("src/res/images/bg.png");
        backgound.setImage(backgound.getImage().getScaledInstance(768,576,Image.SCALE_DEFAULT));

        JLabel jLabel=new JLabel(backgound);

        jLabel.setBounds(0,0,768,576);

        JPanel imagePanel;
        imagePanel =(JPanel)start.getContentPane();
        imagePanel.setOpaque(false);
        imagePanel.setLayout(null);


        /*
        JPanel jPanel=new JPanel();
        jPanel.setBackground(Color.GRAY);
        jPanel.setLayout(null);
        jPanel.setBounds(0,0,768,576);
        imagePanel.add(jPanel);
         */

        JButton jButton=new JButton();
        jButton.setText("进入G大");
        jButton.setBounds(334,330,110,40);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setVisible(false);

                JFrame window = new JFrame("Game");
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setResizable(false);

                GamePanel gamePanel = new GamePanel();

                window.add(gamePanel);

                window.validate();
                window.pack();

                window.setLocationRelativeTo(null);
                window.setVisible(true);

                gamePanel.setupGame();
                gamePanel.startGameThread();

            }
        });

        imagePanel.add(jButton);
        start.getLayeredPane().setLayout(null);
        start.getLayeredPane().add(jLabel,new Integer(Integer.MIN_VALUE));




        /*
        JFrame window = new JFrame("Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();

        //window.remove(jPanel);

        window.add(gamePanel);

        window.validate();
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
<<<<<<< HEAD

        gamePanel.setupGame();
=======
>>>>>>> 72cf2d8e6ef3e96e148871fa92cf6ba3b17a636d
        gamePanel.startGameThread();

         */

/*
        window.setSize(768,576);

        JPanel jPanel=new JPanel();
        jPanel.setBackground(Color.GRAY);
        jPanel.setLayout(null);
        jPanel.setBounds(0,0,768,576);
        window.add(jPanel);


        JButton jButton=new JButton();
        jButton.setText("开始游戏");
        jButton.setBounds(334,230,100,50);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bigin[0] =1;
            }
        });

        jPanel.add(jButton);

        window.setLocationRelativeTo(null);
        window.setVisible(true);


        while (true){
            if (bigin[0] ==1){
                window.remove(jPanel);

                window.add(gamePanel);

                window.validate();
                window.pack();

                //window.setLocationRelativeTo(null);
                //window.setVisible(true);
                gamePanel.startGameThread();
                break;
            }else {
              System.out.flush();
            }
        }

 */












    }
}
