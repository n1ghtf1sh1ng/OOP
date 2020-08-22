package oop.pattern.strategy;

public class SorterBubble implements Sorter { //Strategy
    private static SorterBubble instance = new SorterBubble();

    protected SorterBubble() {}

    public static SorterBubble getInstance() {
        return instance;
    }

    public void sort(int[] data) {
        for (int i = 0; i < data.length; ++i) {
            for (int j = i + 1; j < data.length; ++j) {
                if (data[i] > data[j]) {
                    int v = data[j];
                    data[j] = data[i];
                    data[i] = v;
                }
            }
        }
    }
}
