package test;

import singletonproject.Logger;

public class SingletonTest {
    public static void main(String[] args) {
        
        Logger log1 = Logger.getInstance();
        Logger log2 = Logger.getInstance();

        log1.log("logger1");
        log2.log("logger2");

        if (log1 == log2) {
            System.out.println("same instance");
        } else {
            System.out.println("different instances");
        }
    }
}
