package lhexanome.optimodlivraison.ui;

import lhexanome.optimodlivraison.platform.HelloWorld;
import lhexanome.optimodlivraison.ui.welcome.WelcomeController;
import lhexanome.optimodlivraison.ui.welcome.WelcomeView;
import lhexanome.optimodlivraison.ui.welcome.WelcomeWindow;

import javax.swing.*;
import java.io.File;
import java.io.PrintStream;

/**
 * IHM Entry point.
 * From: https://www.jetbrains.com/help/idea/getting-started-with-gradle.html
 */
public final class HelloMars {

    /**
     * Empeche d'instancier la classe.
     */
    private HelloMars() {
    }

    /**
     * Main method.
     * @param args System args
     */
    public static void main(String[] args) {
        WelcomeWindow welcomeWindow = new WelcomeWindow(new WelcomeController(){
            public void selectMap(File xmlMapFile) {
                System.out.println("test HelloMars : selectMap("+xmlMapFile.getAbsolutePath()+")");
            }
        });

        welcomeWindow.open();

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
