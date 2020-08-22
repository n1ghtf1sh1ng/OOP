package oop.pattern.guimvc;

import oop.pattern.activerecord.Product;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class AccountingTable { //Controller

    JPanel view;
    JTable table;
    AccountingTableModel tableModel; //(View) Model

    TableSelectionChanger selectionChanger;

    public AccountingTable() {
        this.tableModel = new AccountingTableModel();
        this.view = new JPanel();
        this.view.setLayout(new BorderLayout());

        this.view.add(initToolPane(), BorderLayout.NORTH);

        this.table = new JTable(tableModel);
        this.selectionChanger = new TableSelectionChanger(this);
        this.table.getSelectionModel().addListSelectionListener(selectionChanger);
        this.view.add(new JScrollPane(this.table), BorderLayout.CENTER);

        this.tableModel.setObserver(new TableProductObserverMediator(this.selectionChanger));
    }

    private JComponent initToolPane() {
        JToolBar bar = new JToolBar();
        bar.add(new NewRowAction(this));
        bar.add(new DeleteRowAction(this));
        return bar;
    }

    public JPanel getView() {
        return view;
    }

    public void setProducts(List<Product> products) {
        this.tableModel.setProducts(products);
    }

    public List<Product> getProducts() {
        return this.tableModel.getProducts();
    }

    public void setSelected(Product p) {
        int i = this.tableModel.getProducts().indexOf(p);
        this.table.setRowSelectionInterval(i, i);
    }

    public TableSelectionChanger getSelectionChanger() {
        return selectionChanger;
    }

    public void addNewProduct() {
        this.tableModel.addNewProduct();
        this.table.repaint();
    }

    public void deleteProduct() {
        int i = this.table.getSelectedRow();
        if (i >= 0) {
            this.tableModel.deleteProduct(this.tableModel.getProducts().get(i));
            this.table.repaint();
        }
    }

    static class AccountingTableModel extends AbstractTableModel {
        List<Product> products = Collections.emptyList();
        TableProductObserver observer;

        static List<String> columns = Arrays.asList(
            "Name",
            "Price",
            "Stock",
            "Manufacturer");

        public void setProducts(List<Product> products) {
            this.products = products;
            this.fireTableDataChanged();
        }

        public int getRowCount() {
            return products.size();
        }

        public int getColumnCount() {
            return columns.size();
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setObserver(TableProductObserver observer) {
            this.observer = observer;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Product p = this.products.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return p.getName();
                case 1:
                    return p.getPrice();
                case 2:
                    return p.getStock();
                case 3:
                    return p.getManufacturer();
            }
            return null;
        }

        public String getColumnName(int column) {
            return columns.get(column);
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Product p = this.products.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    p.setName(aValue.toString());
                    break;
                case 1:
                    p.setPrice(Long.valueOf(aValue.toString()));
                    break;
                case 2:
                    p.setStock(Integer.valueOf(aValue.toString()));
                    break;
                case 3:
                    p.setManufacturer(aValue.toString());
                    break;
            }
            p.update();
            if (this.observer != null) {
                this.observer.updated(p);
            }
        }

        public void addNewProduct() {
            int id = 0;
            for (Product p : this.products) {
                id = Math.max(p.getId(), id);
            }
            id++;

            int row = this.products.size();
            Product p = new Product();
            p.setName("?");
            p.setManufacturer("?");
            p.setId(id);
            p.insert();
            this.products.add(p);
            this.fireTableRowsInserted(row, row);
            if (this.observer != null) {
                this.observer.added(p);
            }
        }

        public void deleteProduct(Product p) {
            int i = this.products.indexOf(p);
            p.delete();
            this.products.remove(p);
            this.fireTableRowsDeleted(i, i);
            if (this.observer != null) {
                this.observer.deleted(p);
            }
        }
    }

    interface TableProductObserver {
        void added(Product p);
        void deleted(Product p);
        void updated(Product p);
    }

    static class TableProductObserverMediator implements TableProductObserver {
        TableSelectionChanger changer;

        public TableProductObserverMediator(TableSelectionChanger changer) {
            this.changer = changer;
        }

        public void added(Product p) {
            changer.getMediator().updateFromTable();
        }

        public void deleted(Product p) {
            changer.getMediator().updateFromTable();
        }

        public void updated(Product p) {
            changer.getMediator().updateFromTable();
        }
    }

    static class TableSelectionChanger implements ListSelectionListener {
        AccountingTable owner;
        ProductMediator mediator;

        public TableSelectionChanger(AccountingTable owner) {
            this.owner = owner;
        }

        public void setMediator(ProductMediator mediator) {
            this.mediator = mediator;
        }

        public ProductMediator getMediator() {
            return mediator;
        }

        public void valueChanged(ListSelectionEvent e) {
            int i = e.getFirstIndex();
            if (i >= 0 && this.mediator != null) {
                this.mediator.select(this.owner.getProducts().get(i), this.owner);
            }
        }
    }


    static class NewRowAction extends AbstractAction {
        AccountingTable owner;

        public NewRowAction(AccountingTable owner) {
            this.owner = owner;
            this.putValue(NAME, "New Product");
        }

        public void actionPerformed(ActionEvent e) {
            this.owner.addNewProduct();
        }
    }

    static class DeleteRowAction extends AbstractAction {
        AccountingTable owner;

        public DeleteRowAction(AccountingTable owner) {
            this.owner = owner;
            this.putValue(NAME, "Delete Product");
        }

        public void actionPerformed(ActionEvent e) {
            this.owner.deleteProduct();
        }
    }
}
