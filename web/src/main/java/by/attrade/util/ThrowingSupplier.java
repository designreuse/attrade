package by.attrade.util;

@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Exception;
}
