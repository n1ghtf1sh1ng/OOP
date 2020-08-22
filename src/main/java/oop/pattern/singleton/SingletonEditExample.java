package oop.pattern.singleton;

import oop.drawing.moving.EditExample;
import oop.pattern.abstractfactory.FactoryEditExample;
import oop.pattern.builder.FigureFactoryDefaultWithBuilder;

import javax.swing.*;

public class SingletonEditExample extends EditExample {
    public static void main(String[] args) {
        FactoryEditExample ext = new FactoryEditExample();
        ext.setFactory(FigureFactoryDefaultSingleton.getInstance());
        SwingUtilities.invokeLater(ext);
    }

}
