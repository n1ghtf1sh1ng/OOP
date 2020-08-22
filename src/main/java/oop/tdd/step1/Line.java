package oop.tdd.step1;

class Line {
    int x;
    int y;
    int endX;
    int endY;

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setEndXY(int x, int y) {
        this.endX = x;
        this.endY = y;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }
}
