package lhexanome.optimodlivraison.ui;


import lhexanome.optimodlivraison.platform.HelloWorld;

import java.io.PrintStream;

/**
 * From: https://www.jetbrains.com/help/idea/getting-started-with-gradle.html
 */
public class HelloMars {

    public static void main(String[] args) {
        print(System.out);
    }

    public static void print(PrintStream out) {
        out.print("Hello, Mars! and ");
        HelloWorld.print(out);
    }
}
