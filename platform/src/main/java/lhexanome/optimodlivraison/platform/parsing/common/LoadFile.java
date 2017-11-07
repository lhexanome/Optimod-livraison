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
 * Util function to load a XML File.
 */
public final class LoadFile {

    /**
     * Prevent instantiation.
     */
    private LoadFile() {
    }

    /**
     * Load a XML file.
     *
     * @param inputFile File to load
     * @return Root element
     * @throws JDOMException If the file is not a correct XML file.
     * @throws IOException   If unable to read the file
     */
    public static Element loadFromFile(File inputFile) throws JDOMException, IOException {

        return load(new FileInputStream(inputFile));
    }

    /**
     * Load XML from an input stream.
     *
     * @param inputStream InputStream to read
     * @return Root element of the XML
     * @throws JDOMException If the file is not a correct XML file
     * @throws IOException   If unable to read the file
     */
    public static Element load(InputStream inputStream) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(inputStream);
        return document.getRootElement();
    }
}
