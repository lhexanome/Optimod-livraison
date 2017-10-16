package lhexanome.optimodlivraison.platform.parsing.common;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoadFile {

    private LoadFile() {
    }

    public static Element loadFromFile(File inputFile) throws JDOMException, IOException {

        return load(new FileInputStream(inputFile));
    }


    public static Element load(InputStream inputStream) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(inputStream);

        return document.getRootElement();
    }
}
