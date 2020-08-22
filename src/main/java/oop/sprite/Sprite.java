package oop.sprite;

class Sprite {
    int x = 0; //0 means left of the window
    int y = 0; //0 means top of the window
    int direction = 90; //0 means the top direction. '+' means clockwise rotation of degree.

    void move(int d) {
        double r = Math.toRadians((this.direction - 90) % 360);
        this.x += (int) (d * Math.cos(r));
        this.y += (int) (d * Math.sin(r));

        System.out.println("move " + d + ": (x=" + this.x + ", y=" + this.y + ", direction=" + this.direction + ")");
        this.display();
    }

    void rotate(int deg) {
        this.direction += deg;
    }


    void display() {
        try {
            SpriteDisplay.getDisplay().draw(this);
            Thread.sleep(50);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
