package oop.exercise;

import csl.infolab.SetupChecking;
import csl.infolab.TestCodeRunner;
import oop.test.InfolabRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

@RunWith(InfolabRunner.class)
public class Basic00SetupCheck {
    @Test
    public void test01SetupCheck() throws Exception {
        new SetupChecking().check();
    }

    @Rule
    public Timeout globalTimeout = TimeoutSettings.getTimeout();

}
