package by.attrade.util;

import java.util.List;

public class ErrorWrapper {
    public static void runAndWrap(String mark, ThrowingRunnable r, List<Pair<String, Exception>> errors) {
        try {
            r.run();
        } catch (Exception e) {
            errors.add(Pair.of(mark, e));
        }
    }

    public static Object getAndWrap(String mark, ThrowingSupplier s, List<Pair<String, Exception>> errors) {
        Object obj = null;
        try {
            obj = s.get();
        } catch (Exception e) {
            errors.add(Pair.of(mark, e));
        }
        return obj;
    }
}
