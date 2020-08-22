package oop.stack;

public class StackExample {
    public static void main(String[] args) {
        Stack s = new ArrayStack();
        s.push(10);
        s.push(20);
        System.out.println(s + ": " + s.pop() + " empty=" + s.isEmpty());
        System.out.println(s + ": " + s.pop() + " empty=" + s.isEmpty());
        System.out.println(s + ": empty=" + s.isEmpty());

        s = new LinkedStack();
        s.push(10);
        s.push(20);
        System.out.println(s + ": " + s.pop() + " empty=" + s.isEmpty());
        System.out.println(s + ": " + s.pop() + " empty=" + s.isEmpty());
        System.out.println(s + ": empty=" + s.isEmpty());

    }
}
