package oop.pattern.composite;

import oop.drawing.moving.EditExample;
import oop.drawing.moving.EditView;
import oop.drawing.moving.Line;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CompositeEditExample extends EditExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CompositeEditExample());
    }

    @Override
    public void initToolPane(JPanel toolPane) {
        super.initToolPane(toolPane);

        JButton groupButton = new JButton(new NewGroupAction(this.view));
        toolPane.add(groupButton);
    }

    public class NewGroupAction extends AbstractAction {
        EditView view;

        public NewGroupAction(EditView view) {
            this.view = view;
            putValue(NAME, "Example Group");
        }

        public void actionPerformed(ActionEvent e) {
            FigureGroup g = new FigureGroup();

            Line line1 = new Line();
            line1.move(100, 0);
            line1.setEndPoint(0, 100);
            g.addFigure(line1);

            Line line2 = new Line();
            line2.move(0, 100);
            line2.setEndPoint(100, 200);
            g.addFigure(line2);

            Line line3 = new Line();
            line3.move(100, 200);
            line3.setEndPoint(200, 100);
            g.addFigure(line3);

            Line line4 = new Line();
            line4.move(200, 100);
            line4.setEndPoint(100, 0);
            g.addFigure(line4);

            g.move(300, 300);

            this.view.addFigure(g);
        }
    }
}
