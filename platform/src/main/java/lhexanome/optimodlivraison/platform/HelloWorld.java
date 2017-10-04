package lhexanome.optimodlivraison.platform;


import java.io.PrintStream;

/**
 * From: https://www.jetbrains.com/help/idea/getting-started-with-gradle.html
 */
public class HelloWorld {

    public static void main(String[] args) {
        print(System.out);
    }

    public static void print(PrintStream out) {
        out.print("Hell, World!");
    }
}
