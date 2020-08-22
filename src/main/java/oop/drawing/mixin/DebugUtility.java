package oop.drawing.mixin;

public interface DebugUtility {
    int incrementDebugCount();

    default void log(String msg) {
        int count = this.incrementDebugCount();
        System.err.println("[" + count + "] " + msg);
    }
}
