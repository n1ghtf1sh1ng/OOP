package oop.concurrent.thread;

public class ThreadExample {
    public static void main(String[] args) {
        new Thread(new Hello()).start();
        new Thread(new World()).start();

        //by anonymous class
        new Thread(new Runnable() {
            public void run() {
                System.out.println("Hello, ");
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                System.out.println("world!");
            }
        }).start();


        //by lambda
        new Thread(() -> {
            System.out.println("Hello, ");
        }).start(); //as statements
        new Thread(() -> System.out.println("world!")).start(); //as expression

    }
}
