package oop.drawing.multiext;

import oop.drawing.moving.EditExample;
import oop.drawing.moving.EditView;
import oop.drawing.moving.Line;
import oop.drawing.moving.Rectangle;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MEEditExample extends EditExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MEEditExample());
    }

    /**
     * extending tool-panel construction.
     *  2 buttons will be added.
     */
    public void initToolPane(JPanel toolPane) {
        super.initToolPane(toolPane);

        JButton extLine = new JButton(new NewExtLineAction(this.view));
        toolPane.add(extLine);

        JButton extRect = new JButton(new NewExtRectAction(this.view));
        toolPane.add(extRect);
    }

    class NewExtLineAction extends AbstractAction {
        EditView view;

        NewExtLineAction(EditView view) {
            this.view = view;
            putValue(NAME, "Ext Line");
        }

        public void actionPerformed(ActionEvent e) {
            Line line = new MEColoredLabeledLine();
            line.move(100, 100);
            line.setEndPoint(200, 200);
            this.view.addFigure(line);
        }
    }

    class NewExtRectAction extends AbstractAction {
        EditView view;

        NewExtRectAction(EditView view) {
            this.view = view;
            putValue(NAME, "Ext Rectangle");
        }

        public void actionPerformed(ActionEvent e) {
            Rectangle rect = new MEColoredLabeledRectangle();
            rect.move(100, 100);
            rect.setSize(100, 100);
            this.view.addFigure(rect);
        }
    }
}
