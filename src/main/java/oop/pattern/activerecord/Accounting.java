package oop.pattern.activerecord;

public class Accounting {
    public static void main(String[] args) {

        long total = 0;
        for (Product p : Product.getSoldProducts()) {
            p.setPrice((long) (p.getPrice() * 1.08));
            p.update();

            System.out.println(p);

            total += p.getPrice();
        }

        System.out.println("total: " + total);
    }
}
