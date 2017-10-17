package lhexanome.optimodlivraison.ui;

import lhexanome.optimodlivraison.platform.HelloWorld;
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
public class HelloMars implements WelcomeActions, MapPreviewActions {

    WelcomeWindow welcomeWindow;
    MapPreviewWindow mapPreviewWindow;

    /**
     * Main method.
     * @param args System args
     */
    public static void main(String[] args) {
        new HelloMars();
    }
    public HelloMars() {

        welcomeWindow = new WelcomeWindow(this);
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

    @Override
    public void selectMap(File xmlMapFile) {
        System.out.println("test HelloMars : selectMap("+xmlMapFile.getAbsolutePath()+")");

        mapPreviewWindow = new MapPreviewWindow(null, this);
        welcomeWindow.close();
        mapPreviewWindow.open();

    }
}
