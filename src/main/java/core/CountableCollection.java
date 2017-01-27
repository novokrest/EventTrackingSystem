package core;

public interface CountableCollection<T> extends Iterable<T> {
    void add(T element, int count);
    void remove(T element, int count);
    int count(T element);
}
