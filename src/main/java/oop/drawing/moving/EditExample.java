package oop.drawing.moving;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditExample implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new EditExample());
    }

    JFrame frame;
    public EditView view;

    public void run() {
        this.frame = new JFrame("Edit Example");

        JPanel framePane = new JPanel();
        framePane.setLayout(new BorderLayout());

        this.initView();
        framePane.add(this.view, BorderLayout.CENTER);

        JPanel toolPane = new JPanel();
        this.initToolPane(toolPane);
        framePane.add(toolPane, BorderLayout.NORTH);


        this.frame.add(framePane);

        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    public void initView() {
        this.view = new EditView();
    }

    public void initToolPane(JPanel toolPane) {
        //create a button with the clicked action which adds a new figure to the view.
        JButton lineButton = new JButton(new NewLineAction(this.view));
        toolPane.add(lineButton);

        JButton rectButton = new JButton(new NewRectAction(this.view));
        toolPane.add(rectButton);

        JButton circleButton = new JButton(new NewCircleAction(this.view));
        toolPane.add(circleButton);
    }

    //// the following classes are so-called "internal classes".
    ///   Those internal classes are contained in the enclosing class, EditExample.

    public class NewLineAction extends AbstractAction {
        EditView view;
        public NewLineAction(EditView v) {
            this.view = v;
            this.putValue(NAME, "Line"); //the button name
        }
        public void actionPerformed(ActionEvent e) {
            //creating a line and setting up it.
            Line line = new Line();
            line.move(100, 100);
            line.setEndPoint(200, 200);

            //adding the line to the view by calling addFigure(...)
            view.addFigure(line);
        }
    }

    public class NewRectAction extends AbstractAction {
        EditView view;
        public NewRectAction(EditView v) {
            this.view = v;
            this.putValue(NAME, "Rectangle");
        }
        public void actionPerformed(ActionEvent e) {
             //TODO implement
        }
    }

    public class NewCircleAction extends AbstractAction {
        EditView view;
        public NewCircleAction(EditView v) {
            this.view = v;
            this.putValue(NAME, "Circle");
        }
        public void actionPerformed(ActionEvent e) {
             //TODO implement
        }
    }

}
