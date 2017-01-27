package core;

import java.util.Iterator;
import java.util.List;

public class ListCircularIterable<T> implements Iterable<T> {
    private final List<T> list;
    private final int startPosition;

    public ListCircularIterable(List<T> list, int startPosition) {
        this.list = list;
        this.startPosition = startPosition;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListCircularIterator<>(list, startPosition);
    }
}
