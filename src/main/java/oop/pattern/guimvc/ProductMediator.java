package oop.pattern.guimvc;

import oop.pattern.activerecord.Product;

public class ProductMediator { //Mediator pattern
    AccountingTable table;
    AccountingChart chart;

    public ProductMediator(AccountingTable table, AccountingChart chart) {
        this.table = table;
        this.chart = chart;
        table.getSelectionChanger().setMediator(this);
        chart.getSelectionChanger().setMediator(this);
    }

    public void select(Product p, Object source) {
        if (table != source) {
            table.setSelected(p);
        }
        chart.setSelected(p);
    }

    public void updateFromTable() {
        chart.getView().repaint();
    }
}
