package Main;

import MapEditor.Editor.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class start extends JFrame {

    private JFrame start;

    public start(){
        start=new JFrame("Start");
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setSize(768,576);
        start.setResizable(false);
        start.setVisible(true);
        start.setLocationRelativeTo(null);




        ImageIcon backgound = new ImageIcon("src/res/images/Mbg.png");
        backgound.setImage(backgound.getImage().getScaledInstance(768,576, Image.SCALE_DEFAULT));
        JLabel jLabel=new JLabel(backgound);
        jLabel.setBounds(0,0,768,576);

        JPanel imagePanel;
        imagePanel =(JPanel)start.getContentPane();
        imagePanel.setOpaque(false);
        imagePanel.setLayout(null);




        JButton jButton=new JButton();
        jButton.setText("Enter");
        jButton.setBounds(325,305,150,50);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setVisible(false);
                Game game=new Game();
            }
        });

        Font fonte=new Font("consolas",Font.PLAIN,40);
        Font font1=new Font("jetbrains mono",Font.PLAIN,30);
        //consolas,jetbrains mono,Chalkboard,Comic Code Ligatures,Futura, Monaco
        jButton.setFont(fonte);

        jButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                jButton.setForeground(Color.gray);
                getStart().validate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton.setForeground(Color.black);
                getStart().validate();
            }
        });
      //  jButton.setForeground(Color.gray);


        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);

        imagePanel.add(jButton);


        JButton jButton1=new JButton();
        jButton1.setText("设置");
        jButton1.setBounds(60,310,150,40);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        jButton1.setFont(font1);
        jButton1.setForeground(Color.CYAN);

        jButton1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                jButton1.setForeground(Color.MAGENTA);
                getStart().validate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton1.setForeground(Color.CYAN);
                getStart().validate();
            }
        });
        //  jButton.setForeground(Color.gray);


        jButton1.setContentAreaFilled(false);
        jButton1.setBorderPainted(false);



        imagePanel.add(jButton1);



        JButton jButton2=new JButton();
        jButton2.setText("操作说明");
        jButton2.setBounds(60,350,160,40);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        jButton2.setFont(font1);
        jButton2.setForeground(Color.WHITE);

        jButton2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                jButton2.setForeground(Color.MAGENTA);
                getStart().validate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton2.setForeground(Color.WHITE);
                getStart().validate();
            }
        });
        //  jButton.setForeground(Color.gray);


        jButton2.setContentAreaFilled(false);
        jButton2.setBorderPainted(false);


        imagePanel.add(jButton2);

        JButton jButton3=new JButton();
        jButton3.setText("地图编辑");
        jButton3.setBounds(60,390,160,40);
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window window=new window();
            }
        });

        jButton3.setFont(font1);
        jButton3.setForeground(Color.CYAN);

        jButton3.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                jButton3.setForeground(Color.MAGENTA);
                getStart().validate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton3.setForeground(Color.CYAN);
                getStart().validate();
            }
        });
        //  jButton.setForeground(Color.gray);


        jButton3.setContentAreaFilled(false);
        jButton3.setBorderPainted(false);


        imagePanel.add(jButton3);


        JButton jButton4=new JButton();
        jButton4.setText("退出游戏");
        jButton4.setBounds(60,430,160,40);
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        jButton4.setFont(font1);
        jButton4.setForeground(Color.CYAN);

        jButton4.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                jButton4.setForeground(Color.MAGENTA);
                getStart().validate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton4.setForeground(Color.CYAN);
                getStart().validate();
            }
        });
        //  jButton.setForeground(Color.gray);


        jButton4.setContentAreaFilled(false);
        jButton4.setBorderPainted(false);


        imagePanel.add(jButton4);




        start.getLayeredPane().setLayout(null);
        start.getLayeredPane().add(jLabel,new Integer(Integer.MIN_VALUE));




    }





    public JFrame getStart() {
        return start;
    }
}
