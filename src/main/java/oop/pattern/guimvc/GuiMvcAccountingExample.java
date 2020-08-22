package oop.pattern.guimvc;

import oop.pattern.activerecord.Product;

import javax.swing.*;
import java.util.List;

public class GuiMvcAccountingExample implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GuiMvcAccountingExample());
    }

    JFrame frame;
    AccountingTable table;
    AccountingChart chart;

    public void run() {
        frame = new JFrame("Account");

        table = new AccountingTable();
        chart = new AccountingChart();

        List<Product> data = Product.getSoldProducts();
        table.setProducts(data);
        chart.setProducts(data);
        new ProductMediator(table, chart);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                table.getView(),
                new JScrollPane(chart.getView()));
        split.setDividerLocation(200);

        frame.add(split);
        frame.pack();
        frame.setVisible(true);
    }
}
