package lhexanome.optimodlivraison.ui;

import lhexanome.optimodlivraison.platform.HelloWorld;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.mappreview.MapPreviewActions;
import lhexanome.optimodlivraison.ui.mappreview.MapPreviewWindow;
import lhexanome.optimodlivraison.ui.welcome.WelcomeActions;
import lhexanome.optimodlivraison.ui.welcome.WelcomeWindow;

import java.io.File;
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
