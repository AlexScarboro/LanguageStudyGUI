import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author alexs
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        LanguageStudyGUI study = new LanguageStudyGUI();

        //Set program to terminate when window is closed
        study.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //study.pack();
        study.setVisible(true);

        String s1 = "SITHA";
        String s2 = "RAMA";
        s1.concat(s2);
        System.out.println(s1);
    }

}
