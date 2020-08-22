package oop.exercise;

import flowcheck.decl.FinderCodeBase;
import oop.stack.ArrayStack;
import oop.stack.Stack;
import oop.test.InfolabRunner;
import oop.test.TestUtil;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

@RunWith(InfolabRunner.class)
public class Exercise02LinkedStackTest {
    static FinderCodeBase code;
    @BeforeClass
    public static void setUpClass() {
        code = FinderCodeBase.parseProject().loadFromProject("oop.test.Exercise02Spec");
    }
    @AfterClass
    public static void tearDownClass() {
        code.report();
        code = null;
    }
    @Test public void test01CodeType() {
        code.checkPackageByTestName("test01CodeType");
    }
    @Test public void test02CodePop() {
        code.checkPackageByTestName("test02CodePop");
    }
    @Test public void test02CodePush() {
        code.checkPackageByTestName("test02CodePush");
    }
    @Test public void test03CodeIsEmpty() {
        code.checkPackageByTestName("test03CodeIsEmpty");
    }

    Stack stack;
    @Before
    public void setUp() {
        stack = new ArrayStack();
    }

    @Test
    public void testEmpty() {
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void test04PushPop() {
        stack.push(123);
        Assert.assertFalse(stack.isEmpty());
        Assert.assertEquals(123, stack.pop());
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void test05PushPop2() {
        stack.push(123);
        stack.push(456);
        Assert.assertFalse(stack.isEmpty());
        Assert.assertEquals(456, stack.pop());
        Assert.assertFalse(stack.isEmpty());
        Assert.assertEquals(123, stack.pop());
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void test06Pushes() {
        for (int i = 0; i < 100; ++i) {
            stack.push(i);
        }
        Assert.assertFalse(stack.isEmpty());
        for (int i = 0; i < 100; ++i) {
            Assert.assertEquals(99 - i, stack.pop());
        }
        Assert.assertTrue(stack.isEmpty());
    }

    @Rule
    public Timeout globalTimeout = TestUtil.getTimeout();
}
