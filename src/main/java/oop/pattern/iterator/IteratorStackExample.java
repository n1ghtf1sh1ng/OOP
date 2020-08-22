package oop.pattern.iterator;

import java.util.Iterator;

public class IteratorStackExample {
    public static void main(String[] args) {
        ArrayStackIterable s = new ArrayStackIterable();
        s.push(123);
        s.push(456);
        s.push(789);

        for (Integer v : s) {
            System.out.println(v);
        }

        /* the above iteration is same as the following code */
        for (Iterator<Integer> iter = s.iterator();
             iter.hasNext(); ) {
            Integer v = iter.next();
            System.out.println(v);
        }
    }
}
