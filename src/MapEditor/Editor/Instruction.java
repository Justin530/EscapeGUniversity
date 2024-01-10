package MapEditor.Editor;

import javax.swing.*;
import java.awt.*;

public class Instruction extends JFrame {
    public Instruction(){
        this.setSize(950, 900);
        this.setVisible(true);
        this.setResizable(true);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Instruction");
        this.setLayout(null);

        JTextArea jTextArea=new JTextArea();
        jTextArea.setFont(new Font("jetbrains mono",Font.PLAIN,20));
        jTextArea.setText("区域介绍:\n" +
                "1.位于最上方的一排按钮为按钮区\n" +
                "2.位于按钮区域下方的网格部分为画图区\n" +
                "3.最下方网格区域为地砖储存区\n" +
                "\n" +
                "按钮区:\n" +
                "选择图片按钮：可以选择一张或者多张图片作为地砖，储存在地砖储存区。\n" +
                "快速选择按钮：选择图片（003.png），会自动将（000.png,001.png.002.png,003.png）录入储存区。\n" +
                "获取地图按钮：将软件要求的（50*50）的地图数组输出。\n" +
                "碰撞状态获取：每一个砖块有可碰撞的和不可碰撞的，将软件要求的碰撞状态输出\n" +
                "地图预览：预览在画图区所完成的整张地图。\n" +
                "读取：将images文件夹（储存所有地砖），collidable.txt(碰撞文件)，map.txt(地图文件)\n" +
                "放于一个路径下，点击地图文件，将此地图读取于本编辑器中。\n" +
                "\n" +
                "画图区:\n" +
                "网格区域：用鼠标将选择的地砖涂抹在此区域。\n" +
                "右侧方向按键：控制视图（地图一共有50*50个方框）。\n" +
                "右下角文字：随方向按键变化，指明视图坐标。\n" +
                "\n" +
                "地砖储存区：\n" +
                "网格区域：储存地砖，点击选定此地转。\n" +
                "左上角文字：显示选中的地砖，地砖编号，及其是否可碰撞。\n" +
                "右上角按钮：改变选中地砖的可碰撞状态。\n" +
                "\n" +
                "附：\n" +
                "地砖尺寸为（48*48），\n" +
                "地砖命名要求（xxx.png）,\n" +
                "不可有重复命名或者断层。\n" +
                "\n"+
                "使用方法:\n"+
                "1.获取的地图信息替换src/res/map中的map.txt。\n"+
                "2.获取的碰撞状态信息替换src/res/map中的collidable.txt。\n"+
                "3.images文件内容替换tile文件内容");

        jTextArea.setEditable(false);



        JScrollPane jScrollPane = new JScrollPane(jTextArea);

        JScrollBar jScrollBar = null;
        jScrollBar = jScrollPane.getVerticalScrollBar();
        jScrollBar.setUnitIncrement(15);

        //不能用，jScrollPane.add(jPanel);
        this.setContentPane(jScrollPane);

    }

}
