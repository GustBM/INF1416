package service;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ReadFile extends JFrame{
	public void MyJFrame() {
        JButton btn = new JButton("open file");
        add(btn);
        btn.addActionListener(e -> {
            selectFile();
        });
        pack();
        setVisible(true);
    }

    public void selectFile() {
        JFileChooser chooser = new JFileChooser();
        // optionally set chooser options ...
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            // read  and/or display the file somehow. ....
            
        } else {
            // user changed their mind
        }
    }
}
