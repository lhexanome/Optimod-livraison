package lhexanome.optimodlivraison.ui;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloMarsTest {

    @Test
    void print() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HelloMars.print(new PrintStream(out));
        String s = out.toString();
        assertEquals("Hello, Mars! and Hello, World!", s);
    }
}