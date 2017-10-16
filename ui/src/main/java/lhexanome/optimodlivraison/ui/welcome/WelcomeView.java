package lhexanome.optimodlivraison.ui.welcome;

import lhexanome.optimodlivraison.ui.FileTypeFilter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WelcomeView {
    private JButton ouvrirUnPlanButton;
    private JPanel mainPanel;
    private WelcomeWindow window;

    public WelcomeView(WelcomeWindow window) {
        this.window = window;

        ouvrirUnPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.clickChoosedFilePlan();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void choosedFilePlan() {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Ouvrir un plan");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new FileTypeFilter("xml"));

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            //TODO LOG
            System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " + chooser.getSelectedFile());

            window.setFilePlan(chooser.getSelectedFile());

        } else {
            System.out.println("No Selection ");
        }
    }
}
