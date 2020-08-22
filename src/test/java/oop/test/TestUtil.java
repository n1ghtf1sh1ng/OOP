package oop.test;

import org.junit.rules.Timeout;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TestUtil {
    public static Timeout getTimeout() {
        return new Timeout(40, TimeUnit.SECONDS);
    }

    public static Set<Long> set(Iterable<Long> ns) {
        return StreamSupport.stream(ns.spliterator(), false)
                .collect(Collectors.toSet());
    }

    public static Set<Long> set(int... ns) {
        return IntStream.of(ns)
                .mapToLong(i -> i)
                .boxed()
                .collect(Collectors.toSet());
    }

    public static void delete(Path p) {
        try {
            if (Files.isRegularFile(p)) {
                Files.delete(p);
            } else if (Files.isDirectory(p)) {
                List<Path> list;
                try (Stream<Path> ps = Files.list(p)) {
                    list = ps.collect(Collectors.toList());
                }
                list.forEach(TestUtil::delete);
                Files.delete(p);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage() + " : " + p, ex);
        }
    }

    public static List<Path> listDir(Path p) {
        List<Path> list = new ArrayList<>();
        try (Stream<Path> ps = Files.list(p)) {
            list = ps.collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}
