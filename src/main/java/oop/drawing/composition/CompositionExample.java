package oop.drawing.composition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class CompositionExample implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CompositionExample());
    }

    JFrame frame;

    public void run() {
        frame = new JFrame(this.toString());

        CompositionExampleView view = new CompositionExampleView();
        view.setUp();

        frame.add(view);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

class CompositionExampleView extends JPanel implements MouseListener  {
    List<CFigure> figures = new ArrayList<>();

    CompositionExampleView() {
        this.setPreferredSize(new Dimension(1000, 1000));
        this.addMouseListener(this);
    }

    void setUp() {
        CLine line1 = new CLine();
        line1.move(400, 500);
        line1.setEndPoint(500, 400);
        figures.add(line1);

         /* //TODO enable the code after implementing CColoredLine
        CColoredLine line2 = new CColoredLine();
        line2.move(500, 400);
        line2.setEndPoint(600, 500);
        figures.add(line2);
         */

         /* //TODO enable the code after implementing CLabelledLine
        CLabeledLine line3 = new CLabeledLine();
        line3.move(400, 500);
        line3.setEndPoint(500, 600);
        figures.add(line3);
         */

         /* //TODO enable the code after implementing CColoredLabelledLine
        CColoredLabeledLine line4 = new CColoredLabeledLine();
        line4.move(500, 600);
        line4.setEndPoint(600, 500);
        figures.add(line4);
         */
    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (CFigure fig : figures) {
            g.setColor(Color.black);
            fig.draw(g);
        }
    }


    public void mouseClicked(MouseEvent e) { //action for mouse clicking
        int[][] move = {{-10, -10}, {10, -10}, {-10, 10}, {10, 10}};
        for (int i = 0; i < figures.size(); ++i) {
            CFigure fig = figures.get(i);
            int[] m = move[i % move.length];
            fig.move(m[0], m[1]);
        }

        this.repaint();
    }

    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
}

