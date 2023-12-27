package tool;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileChooser {
    private static FileChooser fileChooser=new FileChooser();
    private FileChooser(){}

    public static FileChooser getInstance(){
        return fileChooser;
    }

    public JButton getIButton(JFrame jFrame){
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new FileNameExtensionFilter("image(*.jpg,*.png,*.gif)","jpg","png","gif"));
        int result = fc.showOpenDialog(jFrame);

        if (result == JFileChooser.APPROVE_OPTION){
            File file = fc.getCurrentDirectory();
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            JButton jButton=new JButton(icon);
            jButton.setSize(50,80);
            return jButton;
        }else {
            return null;
        }
    }
}
