package oop.drawing.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * the main class for demonstrating drawing Figures
 */
public class BasicExample implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new BasicExample());
         //The below run() method will be executed as a GUI process.
    }

    JFrame frame; //a window object

    public void run() {
        frame = new JFrame(this.toString());
           //creating a new window with the result of toString() as the title of the window

        BasicExampleView view = new BasicExampleView(); //creating a display for the above window
        view.setUp();

        frame.add(view);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

//a displaying panel for drawing figures
class BasicExampleView extends JPanel implements MouseListener  {
    Line line1;
    Rectangle rect1;

    BasicExampleView() {
        this.setPreferredSize(new Dimension(1000, 1000));
        this.addMouseListener(this); //enabling below methods for handling mouse events
    }

    void setUp() {
        //a line object from (500,650) to (650,800)
        this.line1 = new Line();
        this.line1.move(500, 650);
        this.line1.setEndPoint(650, 800);
        this.line1.move(200, 0);
          //and moving (200,0)

        //a rectangle object at (100,100) and size (100,100)
        this.rect1 = new Rectangle();
        this.rect1.move(100, 100);
        this.rect1.setSize(100, 100);
    }

    //the method for drawing figures
    protected void paintComponent(Graphics g) {
        //clear entire display of the panel
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //draw 2 figures with black lines
        g.setColor(Color.black);
        this.line1.draw(g);
        this.rect1.draw(g);
    }


    public void mouseClicked(MouseEvent e) { //action for mouse clicking
        this.line1.move(-10, -10);

        this.rect1.move(10, 10);

        this.repaint(); //reflecting moving of figures
    }

    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
}

