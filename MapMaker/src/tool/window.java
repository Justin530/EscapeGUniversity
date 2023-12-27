package tool;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class window extends JFrame{
    
    

    public window(){


        this.setSize(1000,800);
        this.setVisible(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("MapMaker");
        this.setLayout(new BorderLayout());





       // jButton.setLocation(400,100);


        JPanel jPanel=new JPanel();
        jPanel.setBackground(Color.GRAY);
        jPanel.setLayout(new BorderLayout());

        JPanel jPanel01=new JPanel();
        jPanel.add(BorderLayout.NORTH,jPanel01);
        jPanel01.setSize(768,576);
        jPanel01.setBackground(Color.GRAY);

        JPanel jPanel02=new JPanel();
        jPanel.add(BorderLayout.CENTER,jPanel02);
        jPanel02.setLayout(new GridLayout(16,12));

        JButton jButton =new JButton();
        jButton.setSize(50,80);
        jButton.setIcon(new ImageIcon("src/images/血色地板2x2.png"));
        jPanel01.add(jButton);


        JLabel jLabel=new JLabel();
        jLabel.setIcon(new ImageIcon("src/images/血色地板2x2.png"));
        jPanel02.add(jLabel);




        JButton jButton1=new JButton("选择图片");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc=new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fc.setMultiSelectionEnabled(false);
                fc.setFileFilter(new FileNameExtensionFilter("image(*.jpg,*.png,*.gif)","jpg","png","gif"));
                int result = fc.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION){
                    String path=fc.getSelectedFile().getAbsolutePath();
                    ImageIcon icon = new ImageIcon(path);
                    JButton jButton=new JButton(icon);
                    jPanel01.add(jButton);
                    getJFrame().pack();
                }
            }
        });
        JPanel jPanel1=new JPanel();
        jPanel1.setBackground(Color.GREEN);
        jPanel1.add(jButton1);


        this.add(BorderLayout.CENTER,jPanel);
        this.add(BorderLayout.SOUTH,jPanel1);





        validate();//定义一个组件JLabel非常小，但是后来setText改变它的值以至于无法完全显示，需要它调整组件JLabel的大小
        //与repaint不同

        //JButton jButton =new JButton("选择图片");

        //JScrollPane jScrollPane =new JScrollPane(jPanel);
        //jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        //jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //jScrollPane.setSize(384,288);

        //this.add(jScrollPane);



      //  jScrollPane.setViewportView(jPanel);

      /*
        this.add(jButton);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JButton jButton1=FileChooser.getInstance().getIButton(window.this);
               window.this.add(jButton1);
            }
        });

       */



    }


    private JFrame getJFrame(){
        return this;
    }
}
