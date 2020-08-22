package oop.pattern.abstractfactory;

import oop.drawing.moving.EditExample;
import oop.drawing.moving.EditView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FactoryEditExample extends EditExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new FactoryEditExample());

        //another version
        FactoryEditExample ext = new FactoryEditExample();
        ext.setFactory(new FigureFactoryExt());
        SwingUtilities.invokeLater(ext);
    }

    FigureFactory factory = new FigureFactoryDefault();

    public void setFactory(FigureFactory factory) {
        this.factory = factory;
    }

    public void initToolPane(JPanel toolPane) {
        for (String kind : this.factory.getFigureKinds()) {
            toolPane.add(new JButton(new NewFigureAction(this.view, this.factory, kind)));
        }
    }

    //the generalized version of action for creating a figure
    public class NewFigureAction extends AbstractAction {
        EditView view;
        FigureFactory factory;
        String kind;

        public NewFigureAction(EditView v, FigureFactory f, String kind) {
            this.view = v;
            this.factory = f;
            this.kind = kind;
            this.putValue(NAME, kind);
        }
        public void actionPerformed(ActionEvent e) {
            view.addFigure(factory.createFigure(this.kind));
        }
    }

}
