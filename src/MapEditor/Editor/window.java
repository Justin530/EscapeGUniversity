package MapEditor.Editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.*;
import java.util.Objects;


public class window extends JFrame {


    private JButton[][] jButtons2 = new JButton[10][17];
    int SelectedButtonX;
    int SelectedButtonY;

    public static int[][] nums = new int[51][51];
    int viewx = 0;
    int viewy = 0;

    private JButton[][] jButtons1 = new JButton[16][21];

    int[][] Solids = new int[16][21];
    final int[] SolidsAdd = {0};

    private int[][] j1 = new int[16][21];//储存编号

    final int[] MultiAdd = {0};


    Icon SelectedIcon = new ImageIcon("src/MapEditor/images/001.png");
    int SelectedIconx;
    int SelectedIcony;

    public static ImageIcon[] icons = new ImageIcon[300];


    public window() throws IOException {

        this.setSize(1500, 1000);
        this.setVisible(true);
        this.setResizable(true);
       // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("MapMaker");
        this.setLayout(null);


        JPanel jPaneldi = new JPanel();
        jPaneldi.setLayout(null);
        jPaneldi.setPreferredSize(new Dimension(1500, 1600));//针对JScrollPane
        jPaneldi.setBorder(new EmptyBorder(1500, 1600, 1500, 1600));//针对窗口
        //先把jPanel1添加到jPanel
        //jpanel是容器，布局为null

        //添加JScrollpane滚动，只能用
        JScrollPane jScrollPane = new JScrollPane(jPaneldi);

        JScrollBar jScrollBar = null;
        jScrollBar = jScrollPane.getVerticalScrollBar();
        jScrollBar.setUnitIncrement(15);

        //不能用，jScrollPane.add(jPanel);
        this.setContentPane(jScrollPane);


///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Font font3 = new Font("仿宋", Font.PLAIN, 25);

        JLabel jLabel6 = new JLabel();
        jLabel6.setBounds(550, 500, 500, 50);

        jPaneldi.add(jLabel6);
        jLabel6.setFont(font3);

        jLabel6.setText("右下角方块坐标：(" + (viewx + 9) + "," + (viewy + 16) + ")");
        this.validate();


        JButton jButton2 = new JButton();
        jButton2.setBounds(830, 270, 55, 55);
        jPaneldi.add(jButton2);
        jButton2.setIcon(new ImageIcon("src/MapEditor/ButtonImages/left.png"));
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewy > 0) {
                    viewy--;
                }
                //System.out.println(viewx+" "+viewy+" "+nums[viewx][viewy]);
                update();

                jLabel6.setText("右下角方块坐标：(" + (viewx + 9) + "," + (viewy + 16) + ")");
                getJFrame().validate();
            }
        });




        JButton jButton3 = new JButton();
        jButton3.setBounds(950, 270, 55, 55);
        jPaneldi.add(jButton3);
        jButton3.setIcon(new ImageIcon("src/MapEditor/ButtonImages/right.png"));
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewy < 34) {
                    viewy++;
                }
                // System.out.println(viewx+" "+viewy+" "+nums[viewx][viewy]);
                update();

                jLabel6.setText("右下角方块坐标：(" + (viewx + 9) + "," + (viewy + 16) + ")");
                getJFrame().validate();
            }
        });
        this.validate();


        JButton jButton4 = new JButton();
        jButton4.setBounds(890, 210, 55, 55);
        jPaneldi.add(jButton4);
        jButton4.setIcon(new ImageIcon("src/MapEditor/ButtonImages/up.png"));
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewx > 0) {
                    viewx--;
                }
                // System.out.println(viewx+" "+viewy+" "+nums[viewx][viewy]);

                update();

                jLabel6.setText("右下角方块坐标：(" + (viewx + 9) + "," + (viewy + 16) + ")");
                getJFrame().validate();
            }
        });

        JButton jButton5 = new JButton();
        jButton5.setBounds(890, 330, 60, 60);
        jPaneldi.add(jButton5);
        jButton5.setIcon(new ImageIcon("src/MapEditor/ButtonImages/down.png"));
        this.validate();
        jButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewx < 41) {
                    viewx++;
                }
                //   System.out.println(viewx+" "+viewy+" "+nums[viewx][viewy]);
                update();

                jLabel6.setText("右下角方块坐标：(" + (viewx + 9) + "," + (viewy + 16) + ")");
                getJFrame().validate();
            }
        });

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {


                char c= e.getKeyChar();
                int code =e.getKeyCode();

                if (code==37){
                    if (viewy > 0) {
                        viewy--;
                    }
                    update();

                    jLabel6.setText("右下角方块坐标：(" + (viewx + 9) + "," + (viewy + 16) + ")");
                    getJFrame().validate();

                }else if (code==38){
                    if (viewx > 0) {
                        viewx--;
                    }
                    update();

                    jLabel6.setText("右下角方块坐标：(" + (viewx + 9) + "," + (viewy + 16) + ")");
                    getJFrame().validate();
                }else if (code==39){
                    if (viewy < 34) {
                        viewy++;
                    }
                    update();

                    jLabel6.setText("右下角方块坐标：(" + (viewx + 9) + "," + (viewy + 16) + ")");
                    getJFrame().validate();
                }else if (code==40){
                    if (viewx < 41) {
                        viewx++;
                    }
                    update();

                    jLabel6.setText("右下角方块坐标：(" + (viewx + 9) + "," + (viewy + 16) + ")");
                    getJFrame().validate();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        JButton jButtonx = new JButton();
        jButtonx.setLocation(10, 10);
        jButtonx.setSize(100, 40);
        jPaneldi.add(jButtonx);
        jButtonx.setText("选择图片");
        getJFrame().validate();

        jButtonx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fc.setMultiSelectionEnabled(true);
                fc.setFileFilter(new FileNameExtensionFilter("image(*.jpg,*.png,*.gif)", "jpg", "png", "gif"));
                int result = fc.showOpenDialog(null);


                if (result == JFileChooser.APPROVE_OPTION) {

                    File[] files=fc.getSelectedFiles();

                    for (int i = 0; i <files.length ; i++) {
                        String path1 = files[i].getAbsolutePath();


                        int SelectedIconNum;
                        SelectedIconNum = getSelectedIconNum(path1);

                        ImageIcon icon = new ImageIcon(path1);


                        if (icon.getIconHeight() != 48 && icon.getIconWidth() != 48) {

                            ImageAmplify imageAmplify = null;

                            try {
                                imageAmplify = new ImageAmplify(path1);
                                imageAmplify.zoomBySize(48, 48);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }


                            int index = path1.lastIndexOf(".");
                            String num = path1.substring(index - 3, index);
                            String ext = path1.substring(index);
                            String front = path1.substring(0, index - 3);
                            path1 = front + num + num + ext;

                            ImageIcon icon1 = new ImageIcon(path1);
                            icon = icon1;

                        }
                        jButtons1[getJFrame().Judge1x()][getJFrame().Judge1y()].setIcon(icon);
                        j1[getJFrame().Judge1x()][getJFrame().Judge1y()] = SelectedIconNum;
                        icons[SelectedIconNum] = icon;
                        getJFrame().validate();//验证JFrame容器下面的所有组件，然后重绘和重排各个组件
                    }


                }
            }
        });


        JButton jButton9 = new JButton();
        jButton9.setBounds(120, 10, 100, 40);
        jPaneldi.add(jButton9);
        jButton9.setText("快速选择");
        jButton9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fc.setMultiSelectionEnabled(false);
                fc.setFileFilter(new FileNameExtensionFilter("image(*.jpg,*.png,*.gif)", "jpg", "png", "gif"));
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String path1 = fc.getSelectedFile().getAbsolutePath();


                    int SelectedIconNum;
                    SelectedIconNum = getSelectedIconNum(path1);

                    for (int i = 1; i <= SelectedIconNum; i++) {

                        int indexP = path1.lastIndexOf(".");


                        if (indexP < 3) {

                            continue;

                        } else if (!judgePath(path1)) {

                            continue;


                        } else {
                            String pre = path1.substring(0, indexP - 3);
                            StringBuilder num1 = new StringBuilder(String.valueOf(1000 + i));
                            num1.deleteCharAt(0);
                            String Num = num1.toString();
                            String ext1 = path1.substring(indexP);
                            path1 = pre + num1 + ext1;
                        }

                        ImageIcon icon = new ImageIcon(path1);
                        if (icon.getIconHeight() != 48 && icon.getIconWidth() != 48) {
                            ImageAmplify imageAmplify = null;
                            try {
                                imageAmplify = new ImageAmplify(path1);
                                imageAmplify.zoomBySize(48, 48);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            int index = path1.lastIndexOf(".");
                            String num = path1.substring(index - 3, index);
                            String ext = path1.substring(index);
                            String front = path1.substring(0, index - 3);
                            path1 = front + num + num + ext;
                            ImageIcon icon1 = new ImageIcon(path1);
                            icon = icon1;
                            path1=fc.getSelectedFile().getAbsolutePath();
                            // System.out.println(path1);
                        }
                        jButtons1[getJFrame().Judge1x()][getJFrame().Judge1y()].setIcon(icon);
                        // System.out.println(path1);
                        j1[getJFrame().Judge1x()][getJFrame().Judge1y()] = i;
                        icons[i] = icon;
                        //   System.out.println(getJFrame().Judge1x()+" "+getJFrame().Judge1y()+" "+SelectedIconNum);
                        getJFrame().validate();//验证JFrame容器下面的所有组件，然后重绘和重排各个组件


                    }


                }
            }
        });


        JButton jButton1 = new JButton();
        jButton1.setBounds(230, 10, 100, 40);
        jPaneldi.add(jButton1);
        jButton1.setText("获取地图");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j < 50; j++) {
                        System.out.print(nums[i][j] + " ");
                    }
                    System.out.print("\n");
                }

                System.out.println("\n");
            }
        });


        JButton jButton7 = new JButton();
        jButton7.setBounds(340, 10, 120, 40);
        jPaneldi.add(jButton7);
        jButton7.setText("碰撞状态获取");
        jButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //使用j1[a][b]判断是否为0，如果不为零的话，输出x.png的字符串，然后换行，判断Solids[a][b]是否为零如果为零输出false，如果
                //为1输出true

                for (int i = 0; i <16 ; i++) {//////扩
                    for (int j = 0; j < 20; j++) {
                        if (j1[i][j] != 0) {


                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(1000 + j1[i][j]);
                            stringBuilder.deleteCharAt(0);
                            stringBuilder.append(".png");

                            System.out.println(stringBuilder);

                            if (Solids[i][j] == 0) {
                                System.out.println("true");
                            } else if (Solids[i][j] == 1) {
                                System.out.println("false");
                            }


                        }
                    }
                }
            }
        });


        JButton jButton8 = new JButton();
        jButton8.setBounds(470, 10, 100, 40);
        jPaneldi.add(jButton8);
        jButton8.setText("地图预览");
        jButton8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  System.out.println("地图预览");
                new AllMap(nums, icons);
            }
        });


        JButton jButton10 = new JButton();
        jButton10.setBounds(580,10,100,40);
        jPaneldi.add(jButton10);
        jButton10.setText("读取");
        jButton10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fc.setMultiSelectionEnabled(false);
                fc.setFileFilter(new FileNameExtensionFilter("文本(*.txt)","txt"));
                int result = fc.showOpenDialog(null);


                if (result == JFileChooser.APPROVE_OPTION) {
                    String path1 = fc.getSelectedFile().getAbsolutePath();//假设选择的是Map.txt

                    String path2;
                    int indexD=path1.lastIndexOf('.');
                    String pre=path1.substring(0,indexD-3);
                    path2=pre+"collidable.txt";

                    String path3;
                    int indexI=path1.lastIndexOf('.');
                    String preI=path1.substring(0,indexD-3);
                    path3=preI+"/images/";


                    try {
                        BufferedReader bufferedReader=new BufferedReader(new FileReader(path1));
                        String line;
                        int lineNum=0;
                        while ((line=bufferedReader.readLine())!=null){

                            String[] strings=line.split(" ");
                            for (int i = 0; i < 50; i++) {
                                nums[lineNum][i]=Integer.parseInt(strings[i]);
                            }
                            lineNum++;
                        }


                        File fileImages=new File(path3);

                        File[] filesImages=fileImages.listFiles();
                        for (int i = 0; i < filesImages.length; i++) {
                            ImageIcon icon=new ImageIcon(filesImages[i].getAbsolutePath());
                            int S=getSelectedIconNum(filesImages[i].getAbsolutePath());
                            icons[S]=icon;
                        }

                        BufferedReader bufferedReader1=new BufferedReader(new FileReader(path2));
                        String line1;
                        int imagesNum=0;
                        int imagesX=0;
                        int imagesY=0;
                        while ((line1=bufferedReader1.readLine())!=null){
                            int Selected=getSelectedIconNum(line1);
                            if (imagesY==20){
                                imagesX++;
                                imagesY=0;
                            }
                            j1[imagesX][imagesY]=Selected;
                            jButtons1[imagesX][imagesY].setIcon(icons[Selected]);


                            line1=bufferedReader1.readLine();
                            if (line1.equals("false")){
                                Solids[imagesX][imagesY]=1;
                            }else if (line1.equals("true")){
                                Solids[imagesX][imagesY]=0;
                            }
                            imagesY++;
                        }

                        update();

                        getJFrame().validate();



                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        JButton jButton11=new JButton();
        jButton11.setBounds(690,10,100,40);
        jPaneldi.add(jButton11);
        jButton11.setText("使用说明");
        jButton11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instruction instruction=new Instruction();
            }
        });


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        JLabel jLabel1 = new JLabel("Selected:");
        //  jLabel1.setForeground(new Color(111,222,0));
        jLabel1.setBounds(50, 650, 150, 40);
        jPaneldi.add(jLabel1);
        Font font = new Font("仿宋", Font.PLAIN, 25);
        jLabel1.setFont(font);


        JLabel jLabel2 = new JLabel();
        jLabel2.setBounds(200, 650, 48, 48);
        jPaneldi.add(jLabel2);
        jLabel2.setIcon(SelectedIcon);

        JLabel jLabel3 = new JLabel("num:");
        jLabel3.setBounds(300, 650, 60, 40);
        jPaneldi.add(jLabel3);
        Font font1 = new Font("仿宋", Font.PLAIN, 25);
        jLabel3.setFont(font1);

        JLabel jLabel4 = new JLabel(String.valueOf(j1[SelectedIcony][SelectedIconx]));
        jLabel4.setBounds(360, 658, 50, 30);
        jPaneldi.add(jLabel4);
        Font font2 = new Font("仿宋", Font.PLAIN, 25);
        jLabel4.setFont(font2);

        this.validate();


        JLabel jLabel5 = new JLabel();
        jLabel5.setBounds(400, 658, 150, 30);
        jPaneldi.add(jLabel5);
        if (Solids[SelectedIcony][SelectedIconx] == 1) {
            jLabel5.setText("(Solid)");
        }
        jLabel5.setFont(font2);

        this.validate();


        JButton jButton6 = new JButton();
        jButton6.setBounds(800, 648, 150, 40);
        jPaneldi.add(jButton6);
        jButton6.setText("碰撞状态转换");
        jButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Solids[SelectedIcony][SelectedIconx] == 0) {
                    Solids[SelectedIcony][SelectedIconx] = 1;
                } else if (Solids[SelectedIcony][SelectedIconx] == 1) {
                    Solids[SelectedIcony][SelectedIconx] = 0;
                }

                if (Solids[SelectedIcony][SelectedIconx] == 1) {
                    jLabel5.setText("(Solid)");
                } else if (Solids[SelectedIcony][SelectedIconx] == 0) {
                    jLabel5.setText("");
                }

                getJFrame().validate();
            }
        });


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        JPanel jPanel1 = new JPanel();
        jPanel1.setBounds(50, 700, 960, 720);
        jPanel1.setLayout(new MyLayout1());
        jPaneldi.add(jPanel1);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        for (int i = 0; i < 16; i++) {//扩
            for (int j = 0; j < 20; j++) {

                JButton jButton = new JButton();
                jButton.setBackground(Color.BLACK);
                jButton.setBorder(new LineBorder(Color.GREEN));
                jPanel1.add(jButton);

                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SelectedIcon = jButton.getIcon();

                        jLabel2.setIcon(SelectedIcon);

                        for (int k = 0; k < 16; k++) {//扩
                            for (int l = 0; l < 20; l++) {
                                if (e.getSource() == jButtons1[k][l]) {
                                    SelectedIconx = l;
                                    SelectedIcony = k;
                                    break;
                                }
                            }
                        }

                        SolidsAdd[0] = Solids[SelectedIcony][SelectedIconx];

                        if (Solids[SelectedIcony][SelectedIconx] == 1) {
                            jLabel5.setText("(Solid)");
                        } else if (Solids[SelectedIcony][SelectedIconx] == 0) {
                            jLabel5.setText("");
                        }

                        jLabel4.setText(String.valueOf(j1[SelectedIcony][SelectedIconx]));

                        getJFrame().validate();

                    }
                });
                getJFrame().validate();
                jButtons1[i][j] = jButton;
            }
        }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        JPanel jPanel2 = new JPanel();
        jPanel2.setBounds(50, 80, 768, 432);
        jPanel2.setLayout(new MyLayout1());
        jPaneldi.add(jPanel2);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 16; j++) {

                JButton jButton = new JButton();
                jButton.setBackground(Color.BLACK);
                jButton.setBorder(new LineBorder(Color.GREEN));
                jPanel2.add(jButton);

                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jButton.setIcon(SelectedIcon);


                        for (int k = 0; k < 9; k++) {
                            for (int l = 0; l < 16; l++) {
                                if (e.getSource() == jButtons2[k][l]) {
                                    SelectedButtonX = l;
                                    SelectedButtonY = k;
                                    break;
                                }
                            }
                        }

                        nums[SelectedButtonY + viewx][SelectedButtonX + viewy] = j1[SelectedIcony][SelectedIconx];


                        getJFrame().validate();
                    }
                });

                jButton.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        MultiAdd[0] = 1;

                        for (int k = 0; k < 9; k++) {
                            for (int l = 0; l < 16; l++) {
                                if (e.getSource() == jButtons2[k][l]) {
                                    SelectedButtonX = l;
                                    SelectedButtonY = k;
                                    break;
                                }
                            }
                        }

                        if (MultiAdd[0] == 1) {

                            jButton.setIcon(SelectedIcon);

                            nums[SelectedButtonY + viewx][SelectedButtonX + viewy] = j1[SelectedIcony][SelectedIconx];
                        }

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        MultiAdd[0] = 0;
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        if (MultiAdd[0] == 1) {

                            for (int k = 0; k < 9; k++) {
                                for (int l = 0; l < 16; l++) {
                                    if (e.getSource() == jButtons2[k][l]) {
                                        SelectedButtonX = l;
                                        SelectedButtonY = k;
                                        break;
                                    }
                                }
                            }

                            jButton.setIcon(SelectedIcon);

                            nums[SelectedButtonY + viewx][SelectedButtonX + viewy] = j1[SelectedIcony][SelectedIconx];
                        }


                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });

                getJFrame().validate();
                jButtons2[i][j] = jButton;
            }
        }


    }


    public window getJFrame() {
        return this;
    }

    public int Judge1x() {
        for (int i = 0; i < 16; i++) {//扩
            for (int j = 0; j < 20; j++) {
                if (j1[i][j] == 0) {
                    return i;
                }
            }
        }
        return 0;

    }

    public int Judge1y() {
        for (int i = 0; i < 16; i++) {//扩
            for (int j = 0; j < 20; j++) {
                if (j1[i][j] == 0) {
                    return j;
                }
            }
        }
        return 0;
    }

    public int getSelectedIconNum(String path) {

        char[] chars = new char[3];

        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '.') {
                chars[0] = path.charAt(i - 3);
                chars[1] = path.charAt(i - 2);
                chars[2] = path.charAt(i - 1);
                break;
            }
        }

        String s = new String(chars);
        return Integer.parseInt(s);
    }


    public void update() {
        //根据viewx和viewy,将nums的viewx+16和viewy+9的数字读取，读取后对应上j1的坐标，设置jButtons1的对应图片

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 16; j++) {


                int numsx = i + viewx;
                int numsy = j + viewy;

                int ImageNum = nums[numsx][numsy];

                if (ImageNum != 0) {
                    jButtons2[i][j].setIcon(icons[ImageNum]);
                    getJFrame().validate();
                } else {
                    jButtons2[i][j].setIcon(new ImageIcon("src/Images/001.png"));
                    getJFrame().validate();
                }


            }
        }

    }

    private boolean judgePath(String path) {
        int index = path.lastIndexOf(".");
        return path.charAt(index - 1) >= 48 && path.charAt(index - 1) <= 57 && path.charAt(index - 2) >= 48 && path.charAt(index - 2) <= 57 && path.charAt(index - 3) >= 48 && path.charAt(index - 3) <= 57;

    }


}
