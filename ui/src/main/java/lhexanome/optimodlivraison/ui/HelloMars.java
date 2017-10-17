package lhexanome.optimodlivraison.ui;

import lhexanome.optimodlivraison.platform.HelloWorld;
import lhexanome.optimodlivraison.ui.controller.Controller;

import java.io.PrintStream;

/**
 * IHM Entry point.
 * From: https://www.jetbrains.com/help/idea/getting-started-with-gradle.html
 */
public class HelloMars {


    /**
     * Main method.
     * @param args System args
     */
    public static void main(String[] args) {

        Controller c = new Controller();


    }


    /**
     * Test case.
     * @param out Stream
     */
    public static void print(PrintStream out) {
        out.print("Hello, Mars! and ");
        HelloWorld.print(out);
    }

}
