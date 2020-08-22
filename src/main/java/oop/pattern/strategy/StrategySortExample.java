package oop.pattern.strategy;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class StrategySortExample {

    public static void main(String[] args) {
        int[] data = new int[100_000];
        Random rand = new Random();
        for (int i = 0; i < data.length; ++i) {
            data[i] = rand.nextInt(1000_000);
        }

        System.out.println("round 1 ==========");
        benchmark(data, SorterBubble.getInstance());
        benchmark(data, SorterQuick.getInstance());
        benchmark(data, SorterBubbleStatic::sort);
             //method reference: convert the static sort method to a Sorter function object.
        System.out.println();

        System.out.println("round 2 ==========");
        benchmark(data, SorterBubble.getInstance());
        benchmark(data, SorterQuick.getInstance());
        benchmark(data, SorterBubbleStatic::sort);
        System.out.println();
    }

    static void benchmark(int[] data, Sorter sorter) {
        System.out.println("-----------");
        System.out.println("Sorter: "  + sorter);
        Instant time = Instant.now();

        sorter.sort(data);
        sorter.sort(data);

        Duration elapsed = Duration.between(time, Instant.now());
        System.out.println("Finish: " + data[0] + "..." + data[data.length / 2] + "..." + data[data.length - 1]);
        System.out.println("Time: " + elapsed);
    }

}
