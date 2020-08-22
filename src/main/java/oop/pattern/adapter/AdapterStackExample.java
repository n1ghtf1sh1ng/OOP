package oop.pattern.adapter;

import oop.pattern.decorator.AuditStack;
import oop.stack.ArrayStack;
import oop.stack.Stack;

public class AdapterStackExample {
    public static void main(String[] args) {
        //regular usage
        Stack s = new ArrayStack();
        myStackOperation(s);

        //usage of an adapter stack
        Stack s2 = new LinkedListStack();
        myStackOperation(s2);
    }

    static void myStackOperation(Stack s) {
        System.out.println("-----------");
        s.push(123);
        s.push(456);
        System.out.println(s.pop());
        System.out.println(s.pop());
    }
}
