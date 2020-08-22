package oop.exercise;

import org.junit.rules.Timeout;

public class TimeoutSettings {
    public static Timeout getTimeout() {
        return new Timeout(20_000);
    }
}
