package oop.pattern.decorator;

import oop.stack.ArrayStack;
import oop.stack.Stack;

public class DecoratorStackExample {
    public static void main(String[] args) {
        //regular usage
        Stack s = new ArrayStack();
        myStackOperation(s);

        //usage of a decorated stack
        Stack s2 = new AuditStack(new ArrayStack());
        myStackOperation(s2);

        ((AuditStack) s2).logCount();
    }

    static void myStackOperation(Stack s) {
        System.out.println("-----------");
        s.push(123);
        s.push(456);
        System.out.println(s.pop());
        System.out.println(s.pop());
    }
}
