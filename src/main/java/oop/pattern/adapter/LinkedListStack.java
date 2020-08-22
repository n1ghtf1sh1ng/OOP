package oop.pattern.adapter;

import oop.stack.Stack;

import java.util.LinkedList;

public class LinkedListStack implements Stack {
    LinkedList<Integer> stack = new LinkedList<>();

    public void push(int v) {
        this.stack.addLast(v);
    }

    public int pop() {
        return this.stack.removeLast();
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }
}
