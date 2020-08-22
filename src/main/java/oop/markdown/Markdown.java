package oop.markdown;


import java.nio.file.Files;
import java.nio.file.Paths;

public class Markdown {
    public static void main(String[] args) {
        String infile = args[0];
        String outfile = args[1];

        new Markdown().convert(infile, outfile);
    }


    public Markdown() {
    }

    public void convert(String infile, String outfile) {
    }

    public String readInput(String infile) {
        try {
            return Files.readString(Paths.get(infile));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void writeOutput(String outfile, String output) {
        try {
            Files.writeString(Paths.get(outfile), output);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
