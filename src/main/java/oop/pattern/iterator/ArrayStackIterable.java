package oop.pattern.iterator;

import oop.stack.Stack;

import java.util.Iterator;

public class ArrayStackIterable implements Stack, Iterable<Integer> {
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

    public Iterator<Integer> iterator() {
        return new ArrayStackIterator(this);
    }
}
