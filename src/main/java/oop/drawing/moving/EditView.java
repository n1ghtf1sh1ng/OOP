package oop.drawing.moving;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class EditView extends JPanel implements MouseListener, MouseMotionListener {
    public List<Figure> figures = new ArrayList<>();

    public EditView() {
        this.setPreferredSize(new Dimension(1000, 1000));
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    public void addFigure(Figure fig) {
        this.figures.add(fig);
        this.repaint();
    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Figure f : figures) {
            g.setColor(Color.black);
            f.draw(g);
        }
    }

    int startX;
    int startY;
    Figure movingFigure; //current moving target

    public void mousePressed(MouseEvent e) {
        //pressed location, which can be the starting position of moving
        this.startX = e.getX();
        this.startY = e.getY();
        this.movingFigure = null;

         //TODO implement

        System.err.println("Pressed: " + this.startY + ", " + this.startY + " : " + this.movingFigure);
        this.repaint();
    }

    public void mouseDragged(MouseEvent e) {
        if (this.movingFigure != null) {
            int dx = e.getX() - this.startX;
            int dy = e.getY() - this.startY;
            System.err.println("Dragged: " +
                    this.startX + "," + this.startY +
                    " + " + dx + "," + dy +
                    " : " + this.movingFigure);

             //TODO implement
        }
        this.repaint();
    }

    public void mouseMoved(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
}
