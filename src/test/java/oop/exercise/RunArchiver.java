package oop.exercise;

import csl.infolab.archiver.ArchiveSetInfolab;
import csl.infolab.archiver.Archiver;
import csl.infolab.archiver.ArchiverGui;

public class RunArchiver {
    public static void main(String[] args) {
        Archiver a = new Archiver();
        ArchiveSetInfolab il = new ArchiveSetInfolab();
        il.setName("oop");
        il.setDescription("Object-Oriented Programming (2020-)");
        /*
        a.addInclusion(".", "report file")
                .addExcludeDot()
                .setRecursive(false)
                .addIncludeSuffix(".pdf");
         */
        il.apply(a);
        new ArchiverGui(a);
    }
}
