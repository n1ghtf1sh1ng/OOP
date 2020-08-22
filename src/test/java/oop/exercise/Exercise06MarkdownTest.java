package oop.exercise;

import flowcheck.decl.FinderCodeBase;
import flowcheck.decl.FinderMethodDecl;
import flowcheck.decl.FinderPackage;
import flowcheck.decl.FinderTypeDecl;
import oop.test.InfolabRunner;
import oop.test.TestUtil;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RunWith(InfolabRunner.class)
public class Exercise06MarkdownTest {

    static FinderCodeBase code;
    @BeforeClass
    public static void setUpClass() {
        code = FinderCodeBase.parseProject().loadFromProject("oop.test.Exercise06Spec");
    }
    @AfterClass
    public static void tearDownClass() {
        code.report();
        code = null;
    }
    @Test
    public void test01CodeMarkdownConvert() throws Exception {
        FinderPackage pack = code.checkPackageByTestName("test01CodeMarkdownConvert");
        FinderTypeDecl md = pack.findTypeByInspectionName("markdown");
        FinderMethodDecl conv = pack.findMethodByInspectionName("convert");

        Path inFile = Paths.get("target" + File.separator + "ex06in.md");
        Path outFile = Paths.get("target" + File.separator + "ex06out.html");
        if (Files.exists(inFile)) {
            Files.delete(inFile);
        }
        if (Files.exists(outFile)) {
            Files.delete(outFile);
        }

        Files.writeString(inFile,
                "# Hello\n" +
                     "This is \n" +
                     "a sample \n" +
                     "paragraph.\n" +
                     "* this is a list\n" +
                     "* next item\n" +
                     "The second\n" +
                     "paragraph.\n" +
                     "* the second list, item 1\n" +
                     "* item 2 of the second list\n");

        Object mainObj = md.toClass().getConstructor().newInstance();
        conv.invoke(mainObj,
                inFile.toString(),
                outFile.toString());

        List<String> expectedOut = Arrays.asList(
                "<html>",
                "<h1>Hello</h1>",
                "<p>",
                "This is",
                "a sample",
                "paragraph.",
                "</p>",
                "<ul>",
                "<li>this is a list</li>",
                "<li>next item</li>",
                "</ul>",
                "<p>",
                "The second",
                "paragraph.",
                "</p>",
                "<ul>",
                "<li>the second list, item 1</li>",
                "<li>item 2 of the second list</li>",
                "</ul>",
                "</html>"
        );

        List<String> actualOut = Files.readAllLines(outFile);

        Assert.assertEquals(expectedOut, actualOut);
    }
    @Rule
    public Timeout globalTimeout = TestUtil.getTimeout();
}
