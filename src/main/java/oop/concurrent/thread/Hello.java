package oop.concurrent.thread;

class Hello implements Runnable {
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("Hello, ");
    }
}
