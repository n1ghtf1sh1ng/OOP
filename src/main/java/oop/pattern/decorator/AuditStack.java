package oop.pattern.decorator;

import oop.stack.Stack;

public class AuditStack implements Stack { //Decorator
    Stack stack;
    int count;

    public AuditStack(Stack stack) {
        this.stack = stack;
        this.count = 0;
    }

    public void push(int v) {
        ++this.count;
        this.stack.push(v);
    }

    public int pop() {
        ++this.count;
        return this.stack.pop();
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public void logCount() {
        System.err.println("count: " + this.count);
    }
}
