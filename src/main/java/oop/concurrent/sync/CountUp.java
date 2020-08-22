package oop.concurrent.sync;

import java.time.Duration;
import java.time.Instant;

public class CountUp {
    public static void main(String[] args) {
        new CountUp().execSingle();
        new CountUp().execParallel(4);
    }

    public void execSingle() {
        execBefore();
        execLoop();
        execAfter();
    }

    Instant time;

    long max = Integer.MAX_VALUE;
    long count = 0;

    public void execBefore() {
        System.out.println("start " + max);
        time = Instant.now();
    }

    public void execLoop() {
         //TODO improve
        for (int i = 0; i < max; ++i) {
            count++;
        }
    }

    public void execAfter() {
        Duration t = Duration.between(time, Instant.now());
        System.out.printf("time: %,d ms\t count: %,d : %s\n",
                t.toMillis(),
                count,
                Thread.currentThread().getName());
    }

    public void execParallel(int threads) {
        max /= threads;
        execBefore();
        for (int i = 0 ; i < threads; ++i) {
            Runnable r = new Runnable() {
                public void run() {
                    execLoop();
                    execAfter();
                }
            };
            new Thread(r).start();
        }
    }
}
