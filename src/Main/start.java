package Main;

import MapEditor.Editor.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class start extends JFrame {

    private JFrame start;

    public start() throws IOException {
        start=new JFrame("Start");
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setSize(768,606);//768*576
        start.setResizable(false);
        start.setVisible(true);
        start.setLocationRelativeTo(null);

        ImageIcon background = new ImageIcon("src/res/images/background.png");
        JLabel jLabel=new JLabel(background);
        jLabel.setBounds(0,0,768,576);
        JPanel imagePanel;

        imagePanel =(JPanel)start.getContentPane();
        imagePanel.setOpaque(false);
        imagePanel.setLayout(null);

        JButton jButton=new JButton();
        jButton.setBounds(193,417,366,71);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setVisible(false);
                Game game=new Game();
            }
        });
        jButton.setIcon(new ImageIcon("src/res/images/ButtonE.png"));
        jButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                jButton.setIcon(new ImageIcon("src/res/images/ButtonE2.png"));
                getStart().validate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton.setIcon(new ImageIcon("src/res/images/ButtonE.png"));
                getStart().validate();
            }
        });
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        imagePanel.add(jButton);


        JButton jButton1=new JButton();
        jButton1.setBounds(610,513,116,25);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        jButton1.setIcon(new ImageIcon("src/res/images/setup.png"));
        jButton1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                jButton1.setIcon(new ImageIcon("src/res/images/setup2.png"));
                getStart().validate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton1.setIcon(new ImageIcon("src/res/images/setup.png"));
                getStart().validate();
            }
        });
        jButton1.setContentAreaFilled(false);
        jButton1.setBorderPainted(false);
        imagePanel.add(jButton1);


        JButton jButton3=new JButton();
        jButton3.setBounds(185,513,383,24);
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    window window=new window();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        jButton3.setIcon(new ImageIcon("src/res/images/mapE.png"));
        jButton3.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                jButton3.setIcon(new ImageIcon("src/res/images/mapE2.png"));
                getStart().validate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton3.setIcon(new ImageIcon("src/res/images/mapE.png"));
                getStart().validate();
            }
        });
        jButton3.setContentAreaFilled(false);
        jButton3.setBorderPainted(false);
        imagePanel.add(jButton3);


        JButton jButton4=new JButton();
        jButton4.setBounds(25,510,125,30);
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jButton4.setIcon(new ImageIcon("src/res/images/exit.png"));
        jButton4.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                jButton4.setIcon(new ImageIcon("src/res/images/exit2.png"));
                getStart().validate();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton4.setIcon(new ImageIcon("src/res/images/exit.png"));
                getStart().validate();
            }
        });
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
