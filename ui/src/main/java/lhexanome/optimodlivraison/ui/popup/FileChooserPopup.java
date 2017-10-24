package lhexanome.optimodlivraison.ui.popup;

import javax.swing.*;
import java.io.File;

public class FileChooserPopup {
    private JFileChooser chooser;

    public FileChooserPopup(String dialogTitle, String extension) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(dialogTitle);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileTypeFilter(extension));
    }


    public File show() {
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }
}
