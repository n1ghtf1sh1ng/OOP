package oop.pattern.guimvc;

import oop.pattern.activerecord.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountingChart  { //Controller
    ChartView view;
    ChartSelectionChanger selectionChanger;

    public AccountingChart() {
        this.view = new ChartView();
        this.selectionChanger = new ChartSelectionChanger(this);
        this.view.addMouseListener(this.selectionChanger);
    }

    public ChartView getView() {
        return view;
    }

    public void setProducts(List<Product> products) {
        this.view.setProducts(products);
    }

    public void setSelected(Product selected) {
        this.view.setSelected(selected);
    }

    public List<Product> getProducts() {
        return this.view.getProducts();
    }

    public ChartSelectionChanger getSelectionChanger() {
        return selectionChanger;
    }

    public static class ChartView extends JPanel {
        List<Product> products = Collections.emptyList();
        Product selected;

        public ChartView() {
            this.setPreferredSize(new Dimension(800, 300));
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
            this.setPreferredSize(new Dimension(1000, Math.max(300, 50 * products.size())));
            this.repaint();
        }

        public void setSelected(Product selected) {
            this.selected = selected;
            this.repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            double max = 0;
            for (Product p : this.products) {
                max = Math.max(p.getPrice(), max);
            }

            List<int[]> rs = this.getProductRectangles();
            int i = 0;
            for (Product p : this.products) {
                if (p == selected) {
                    g.setColor(Color.blue);
                } else {
                    g.setColor(Color.black);
                }
                int[] r = rs.get(i);

                double rt = p.getPrice() / max;

                int barLeft = r[0];
                int barTop = r[1];
                int barRight = r[2];
                int barBottom = r[3];

                int barHeight = barBottom - barTop;

                int productBarHeight = (int) (barHeight * rt);

                g.fillRect(barLeft + 10, barBottom - productBarHeight,
                        barRight - barLeft - 20, productBarHeight);
                String name = p.getName();
                g.drawString(name.substring(0, Math.min(6, name.length())),
                        barLeft + 10, barBottom + 20);

                ++i;
            }
        }

        //{x1,y1, x2,y2}
        public List<int[]> getProductRectangles() {
            int barTop = (int) (this.getHeight() * 0.15);
            int barHeight = (int) (this.getHeight() * 0.7);
            int barX = 0;
            int barWidth = 50;
            List<int[]> rs = new ArrayList<>();
            for (Product p : this.products) {
                rs.add(new int[] {barX, barTop, barX + barWidth, barTop + barHeight});
                barX += barWidth;
            }
            return rs;
        }
    }

    public static class ChartSelectionChanger extends MouseAdapter { //Controller
        AccountingChart owner;
        ProductMediator mediator;

        public ChartSelectionChanger(AccountingChart owner) {
            this.owner = owner;
        }

        public void setMediator(ProductMediator mediator) {
            this.mediator = mediator;
        }

        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int index = 0;
            ChartView view = this.owner.getView();
            for (int[] r : view.getProductRectangles()) {
                if (r[0] <= x && x <= r[2] &&
                    r[1] <= y && y <= r[3]) {
                    Product p = view.getProducts().get(index);
                    if (this.mediator != null) {
                        this.mediator.select(p, this.owner);
                    }
                    break;
                }
                ++index;
            }
        }
    }
}
