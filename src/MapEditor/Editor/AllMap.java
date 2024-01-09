package MapEditor.Editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllMap extends JFrame {

    int[][] numsMap;

    ImageIcon[] iconsMap;

    private static JLabel[][] jLabels=new JLabel[51][51];

   private static JPanel jPaneldi=new JPanel();
    public AllMap(int[][] nums,ImageIcon[] icons){

        numsMap=nums;
        iconsMap=icons;


        this.setSize(1500,1000);
        this.setVisible(true);
        this.setResizable(true);
     //   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("MapMaker");
        this.setLayout(null);

        jPaneldi=new JPanel();
        jPaneldi.setLayout(new MyLayoutMap());
        jPaneldi.setPreferredSize(new Dimension(2600,2600));
        jPaneldi.setBorder(new EmptyBorder(2600,2600,2600,2600));
        JScrollPane jScrollPane = new JScrollPane(jPaneldi);

        JScrollBar bar=null;
        bar=jScrollPane.getVerticalScrollBar();
        bar.setUnitIncrement(25);


        this.setContentPane(jScrollPane);

        JButton jButton1=new JButton();
        jButton1.setBounds(0,0,100,40);
        this.add(jButton1);
        jButton1.setText("刷新");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMap();
            }
        });


        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {

                JLabel jLabel=new JLabel();
                jPaneldi.add(jLabel);

                if (nums[i][j]==0){
                    jLabel.setIcon(new ImageIcon("src/images/001.png"));
                }
                else if(nums[i][j]>0){
                    jLabel.setIcon(icons[nums[i][j]]);
                }

                jLabels[i][j]=jLabel;

                jPaneldi.validate();
            }
        }





    }

   /* public static void main(String[] args) {
        //new AllMap();
    }*/

    public AllMap getJFrame(){
        return this;
    }

    public static JPanel getjPaneldiMap(){
        return jPaneldi;
    }

    public static void updateMap(){

        //System.out.println("jjjjjjj");
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {


                jPaneldi.add(jLabels[i][j]);

                if (window.nums[i][j]==0){
                    jLabels[i][j].setIcon(new ImageIcon("src/images/001.png"));
                }
                else if(window.nums[i][j]>0){
                    jLabels[i][j].setIcon(window.icons[window.nums[i][j]]);
                }
                jPaneldi.validate();
            }
        }
    }

}
