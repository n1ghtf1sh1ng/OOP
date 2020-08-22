package oop.pattern.singleton;

import oop.pattern.abstractfactory.FigureFactoryDefault;

public class FigureFactoryDefaultSingleton extends FigureFactoryDefault {
    private static FigureFactoryDefaultSingleton instance = new FigureFactoryDefaultSingleton();

    protected FigureFactoryDefaultSingleton() {
    }

    public static FigureFactoryDefaultSingleton getInstance() {
        return instance;
    }
}
