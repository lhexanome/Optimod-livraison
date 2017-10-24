package lhexanome.optimodlivraison.ui.popup;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileTypeFilter extends FileFilter {

    private final String type;

    public FileTypeFilter(String type){
        this.type = type;
    }

    @Override
    public boolean accept(File f) {
        return !f.isFile() || f.getAbsolutePath().endsWith("." + type);
    }

    @Override
    public String getDescription() {
        return null;
    }
}
