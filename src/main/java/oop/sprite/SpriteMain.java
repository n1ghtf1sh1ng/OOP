package oop.sprite;

public class SpriteMain {
    public static void main(String[] args) {
        programSafeGuard();
        int left = -240;
        int right = 240;
        //TODO implements
    }

    static void programSafeGuard() {
        new Thread() { //a background task definition
            public void run() {
                try {
                    Thread.sleep(30_000);  //wait milliseconds on background

                    System.out.println("TIMEOUT");
                    System.exit(0); //force to shut down

                } catch (Exception ex) { //error handling
                    throw new RuntimeException(ex);
                }
            }
        }.start();
    }
}
