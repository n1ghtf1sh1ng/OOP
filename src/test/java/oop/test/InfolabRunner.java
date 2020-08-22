package oop.test;

import csl.infolab.TestCodeRunListener;
import csl.infolab.TestCodeRunner;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.*;

public class InfolabRunner extends BlockJUnit4ClassRunner {
    public InfolabRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier runNotifier) {
        new TestCodeGlobalListener().add(runNotifier);
        super.run(runNotifier);
    }

    @Override
    protected List<FrameworkMethod> getChildren() {
        List<FrameworkMethod> list = new ArrayList<>(super.getChildren());
        list.sort(Comparator.comparing(FrameworkMethod::getName));
        return list;
    }

    public static class TestCodeGlobalListener extends RunListener {
        protected final TestCodeRunListenerForDescription impl = TestCodeRunListener.getGlobal(TestCodeRunListenerForDescription.class);

        public void add(RunNotifier runNotifier) {
            synchronized (impl) {
                if (impl.checkAndAdd(runNotifier)) {
                    runNotifier.addListener(this);
                }
            }
        }

        @Override
        public void testStarted(Description description) {
            impl.started(description);
        }

        @Override
        public void testFailure(Failure failure)  {
            impl.addFailure(failure.getMessage(), failure.getDescription());
        }

        @Override
        public void testAssumptionFailure(Failure failure) {
            impl.addFailure(failure.getMessage(), failure.getDescription());
        }

        @Override
        public void testFinished(Description description)  {
            impl.finished(description);
        }

        @Override
        public void testRunFinished(Result result) {
            impl.runFinished();
        }
    }

    public static class TestCodeRunListenerForDescription extends TestCodeRunListener<Description> {
        protected Set<RunNotifier> notifiers = new HashSet<>();

        public boolean checkAndAdd(RunNotifier notifier) {
            if (!notifiers.contains(notifier)) {
                notifiers.add(notifier);
                return true;
            } else {
                return false;
            }
        }

        @Override
        public TestCodeRunner.TestMethod getTestMethodFromDescription(Description desc) {
            try {
                return new TestCodeRunner.TestMethod(
                        desc.getTestClass().getMethod(desc.getMethodName()));
            } catch (Exception ex) {
                log("Error: getTestMethodFromDescription " + ex);
                return new TestCodeRunner.TestMethodName(desc.getClassName(), desc.getMethodName());
            }
        }
    }

}
