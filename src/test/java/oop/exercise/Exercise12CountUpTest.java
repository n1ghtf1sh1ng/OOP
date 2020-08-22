package oop.exercise;

import csl.infolab.OutChecker;
import flowcheck.decl.FinderCodeBase;
import oop.concurrent.sync.CountUp;
import oop.test.InfolabRunner;
import oop.test.TestUtil;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(InfolabRunner.class)
public class Exercise12CountUpTest {
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
    public void test01Code() {
        code.checkPackageByTestName("test01Code");
    }

    @Test
    public void test02Run() throws Exception {

        OutChecker outChecker = new OutChecker();
        CountUp c = new CountUp();
        Instant time = Instant.now();
        c.execSingle();

        Duration singleTime = Duration.between(time, Instant.now());


        time = Instant.now();
        c = new CountUp();
        c.execParallel(4);
        Duration parallelTime = Duration.between(time, Instant.now());

        Thread.sleep(singleTime.toMillis());

        List<Long[]> outs = new ArrayList<>();

        String output = outChecker.takeOutput();

        Pattern outPat = Pattern.compile("time: ([\\d,]+) ms\\s+count: ([\\d,]+)\\s+:.*");
        for (String line: output.split("\n")) {
            Matcher m = outPat.matcher(line);
            if (m.matches()) {
                outs.add(new Long[] {
                        Long.parseLong(m.group(1).replace(",", "")),
                        Long.parseLong(m.group(2).replace(",", ""))
                });
            }
        }

        Assert.assertEquals("the output time lines must be 5: " + output,
                5, outs.size());

        Long[] single = outs.get(0);
        Long[] parallelLast = outs.get(outs.size() - 1);

        Assert.assertTrue("Single thread time > 4 Threads time:" +
                        "single: " + single[0] + " ms vs parallel: " + parallelLast[0] + " ms",
                single[0] > parallelLast[0]);

        Assert.assertTrue("Those counts are almost same value: \n" +
                        "single: "+ single[1] + " vs parallel: " + parallelLast[1],
                (single[1] - parallelLast[1]) <= 4);
    }

    @Rule
    public Timeout globalTimeout = TestUtil.getTimeout();
}
