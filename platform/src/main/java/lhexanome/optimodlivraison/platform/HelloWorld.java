package lhexanome.optimodlivraison.platform;


import java.io.PrintStream;

/**
 * Platform entry point.
 * From: https://www.jetbrains.com/help/idea/getting-started-with-gradle.html
 */
public final class HelloWorld {

    /**
     * Empeche d'instancier la classe.
     */
    private HelloWorld() {
    }

    /**
     * Main method.
     * @param args System args
     */
    public static void main(String[] args) {
        print(System.out);
    }

    /**
     * Test case.
     * @param out Stream
     */
    public static void print(PrintStream out) {
        out.print("Hello, World!");
    }
}
