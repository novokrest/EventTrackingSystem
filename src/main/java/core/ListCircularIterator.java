package core;

import java.util.Iterator;
import java.util.List;

public class ListCircularIterator<E> implements Iterator<E> {
    private final List<E> list;
    private final int startPosition;
    private int traversed;

    public ListCircularIterator(List<E> list, int startPosition) {
        this.list = list;
        this.startPosition = startPosition < 0 ? (startPosition % list.size()) + list.size()
                                               : startPosition;
    }

    public boolean hasNext() {
        return traversed < list.size();
    }

    public E next() {
        int index = (startPosition + traversed) % list.size();
        ++traversed;
        return list.get(index);
    }
}
