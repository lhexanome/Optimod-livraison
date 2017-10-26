package lhexanome.optimodlivraison.ui.popup;

import javax.swing.*;
import java.io.File;

/**
 * Popup to choose a file.
 * The user will be capable of choosing any file.
 * Can only select one file at a time.
 */
public class FileChooserPopup {
    /**
     * Swing file chooser.
     */
    private JFileChooser chooser;

    /**
     * Constructor.
     * Take a dialog title and an extension wanted.
     *
     * @param dialogTitle Dialog title
     * @param extension   Extension string
     */
    public FileChooserPopup(String dialogTitle, String extension) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(dialogTitle);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileTypeFilter(extension));
    }

    /**
     * Show the dialog and return the file selected or `null` if cancel.
     *
     * @return File selected
     */
    public File show() {
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }
}
