package oop.pattern.strategy;

public class SorterQuick implements Sorter {
    private static SorterQuick instance = new SorterQuick();

    protected SorterQuick() {}

    public static SorterQuick getInstance() {
        return instance;
    }

    public void sort(int[] data) {
        quickSort(data, 0, data.length - 1);
    }

    void quickSort(int[] data, int from, int to) {
        if (from < to) {
            int i = from;
            int j = to;
            int pivot = median(data[from], data[from + (to - from) / 2], data[to]);
            while (true) {
                while (data[i] < pivot) {
                    ++i;
                }
                while (pivot < data[j]) {
                    --j;
                }
                if (i >= j) {
                    break;
                }
                int tmp = data[i];
                data[i] = data[j];
                data[j] = tmp;
                ++i;
                --j;
            }
            quickSort(data, from, i - 1);
            quickSort(data, j + 1, to);
        }
    }

    int median(int x, int y, int z) {
        //use of ternary "conditional expression" (cond ? t : f)  //if cond is true, then return t, otherwise f.
        return x < y ?
                (y < z ? y :
                        (z < x ? x : z)) :
                (z < y ? y :
                        (x < z ? x : z));
    }
}
