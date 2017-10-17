package lhexanome.optimodlivraison.platform.parsing.common;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Methodes pour parser un fichier XML.
 */
public final class LoadFile {

    /**
     * Constructeur par défaut non disponible.
     */
    private LoadFile() {
    }

    /**
     * Charge un fichier XML.
     *
     * @param inputFile Fichier à charger
     * @return Element racine du XML
     * @throws JDOMException Si le contenu du fichier n'est pas du XML
     * @throws IOException   S'il y a un problème I/O
     */
    public static Element loadFromFile(File inputFile) throws JDOMException, IOException {

        return load(new FileInputStream(inputFile));
    }

    /**
     * Charge une input au format XML.
     *
     * @param inputStream InputStream à lire
     * @return Element racine du XML
     * @throws JDOMException Si le contenu du stream n'est pas du XML
     * @throws IOException   S'il y a un problème I/O
     */
    public static Element load(InputStream inputStream) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(inputStream);
        return document.getRootElement();
    }
}
