package lhexanome.optimodlivraison.platform.parsing.common;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class LoadFileTest {
    @Test
    void loadFromFile() {

    }

    @Test
    void load() throws IOException, JDOMException {
        String data = "<?xml version = \"1.0\"?>\n<class></class>";

        Element root = LoadFile.load(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8.name())));

        assertEquals(root.getName(), "class");
    }

}