package by.attrade.util;

@FunctionalInterface
public interface ThrowingRunnable<T> {
    void run() throws Exception;
}
