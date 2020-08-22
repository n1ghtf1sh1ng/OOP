package oop.stack;

public class ArrayStack implements Stack {
    int index;
    int[] data = new int[100];

    public void push(int v) {
        this.data[this.index] = v;
        ++this.index;
    }

    public int pop() {
        --this.index;
        return this.data[this.index];
    }

    public boolean isEmpty() {
        return index == 0;
    }
}
