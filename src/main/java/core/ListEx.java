package core;

import java.util.List;

public class ListEx {
    public static <T> Iterable<T> toCircular(List<T> list, int startPosition) {
        return new ListCircularIterable<>(list, startPosition);
    }
}
