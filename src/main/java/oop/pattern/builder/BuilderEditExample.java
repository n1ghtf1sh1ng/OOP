package oop.pattern.builder;

import oop.drawing.moving.EditExample;
import oop.drawing.moving.EditView;
import oop.pattern.abstractfactory.FactoryEditExample;
import oop.pattern.abstractfactory.FigureFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BuilderEditExample extends EditExample {
    public static void main(String[] args) {
        FactoryEditExample ext = new FactoryEditExample();
        ext.setFactory(new FigureFactoryDefaultWithBuilder());
        SwingUtilities.invokeLater(ext);
    }

}
