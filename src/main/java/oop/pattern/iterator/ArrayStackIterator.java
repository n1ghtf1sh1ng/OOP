package oop.pattern.iterator;

import oop.stack.ArrayStack;

import java.util.Iterator;

public class ArrayStackIterator implements Iterator<Integer> {
    int next;
    ArrayStackIterable target;

    public ArrayStackIterator(ArrayStackIterable target) {
        this.target = target;
        this.next = 0;
    }

    public boolean hasNext() {
        return this.next < this.target.index;
    }

    public Integer next() {
        int v = this.target.data[this.next];
        ++this.next;
        return v;
    }
}
