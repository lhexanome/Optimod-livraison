package lhexanome.optimodlivraison.ui.popup;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Simple file type filter.
 */
public class FileTypeFilter extends FileFilter {

    /**
     * Type wanted.
     */
    private final String type;

    /**
     * Constructor.
     *
     * @param type The extension to filter (without the '.')
     */
    public FileTypeFilter(String type) {
        this.type = type;
    }

    /**
     * Per file filter.
     *
     * @param f File
     * @return True if it has the good extension
     */
    @Override
    public boolean accept(File f) {
        return !f.isFile() || f.getAbsolutePath().endsWith("." + type);
    }

    /**
     * Return the description.
     *
     * @return Description
     */
    @Override
    public String getDescription() {
        return "Fichier XML";
    }
}
