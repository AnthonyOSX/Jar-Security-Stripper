package com.swizzbeat.jar_security_stripper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 12/19/14
 */

public class Main {

    public static void main(String... args) {
        File jarFile = promptForJarFile();

        if (jarFile != null) {
            try {
                JarModifier.run(jarFile);
            } catch (IOException e) {
                StringWriter sw = new StringWriter();

                e.printStackTrace(new PrintWriter(sw));
                JOptionPane.showMessageDialog(null, sw.toString());
                e.printStackTrace();
            }
        }
    }

    private static File promptForJarFile() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("JAR Files", "jar"));

        return (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) ? fileChooser.getSelectedFile() : null;
    }

}
