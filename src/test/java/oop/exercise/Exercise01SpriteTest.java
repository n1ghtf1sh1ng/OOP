package oop.exercise;

import flowcheck.decl.FinderCodeBase;
import oop.test.InfolabRunner;
import oop.test.TestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

@RunWith(InfolabRunner.class)
public class Exercise01SpriteTest {
    static FinderCodeBase code;
    @BeforeClass
    public static void setUpClass() {
        code = FinderCodeBase.parseProject().loadFromProject("oop.test.Exercise01Spec");
    }
    @AfterClass
    public static void tearDownClass() {
        code.report();
        code = null;
    }
    @Test
    public void test01Code() {
        code.checkPackageByTestName("test01Code");
    }

    @Rule
    public Timeout globalTimeout = TestUtil.getTimeout();
}
