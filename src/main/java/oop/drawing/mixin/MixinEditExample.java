package oop.drawing.mixin;

import oop.drawing.moving.EditExample;
import oop.drawing.moving.EditView;
import oop.drawing.moving.Line;
import oop.drawing.moving.Rectangle;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MixinEditExample extends EditExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MixinEditExample());
    }

    /**
     * completely rewriting the super-implementation of the method
     */
    public void initToolPane(JPanel toolPane) {
        JButton lineButton = new JButton(new NewDebugLineAction(this.view));
        toolPane.add(lineButton);

        JButton rectButton = new JButton(new NewRectAction(this.view));
        toolPane.add(rectButton);

        JButton circleButton = new JButton(new NewCircleAction(this.view));
        toolPane.add(circleButton);

        JButton extLine = new JButton(new NewExtLineAction(this.view));
        toolPane.add(extLine);

        JButton extRect = new JButton(new NewExtRectAction(this.view));
        toolPane.add(extRect);
    }

    public class NewDebugLineAction extends AbstractAction {
        EditView view;

        public NewDebugLineAction(EditView view) {
            this.view = view;
            putValue(NAME, "Line");
        }

        public void actionPerformed(ActionEvent e) {
            Line line = new DebuggingLine();
            line.move(100, 100);
            line.setEndPoint(200, 200);
            this.view.addFigure(line);
        }
    }

    public class NewExtLineAction extends AbstractAction {
        EditView view;

        public NewExtLineAction(EditView view) {
            this.view = view;
            this.putValue(NAME, "Mixin Line");
        }

        public void actionPerformed(ActionEvent e) {
             Line line = new Line(); //TODO change Line to ColoredLabelledLine
            line.move(100, 100);
            line.setEndPoint(200, 200);
            this.view.addFigure(line);
        }
    }

    public class NewExtRectAction extends AbstractAction {
        EditView view;

        public NewExtRectAction(EditView view) {
            this.view = view;
            putValue(NAME, "Mixin Rectangle");
        }

        public void actionPerformed(ActionEvent e) {
             Rectangle rect = new Rectangle(); //TODO change Rectangle to ColoredLabelledRectangle
            rect.move(100, 100);
            rect.setSize(100, 100);
            this.view.addFigure(rect);
        }
    }
}
