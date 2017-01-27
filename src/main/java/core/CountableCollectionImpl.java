package core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CountableCollectionImpl<T> implements CountableCollection<T> {
    private final Map<T, Integer> countForElement;

    public CountableCollectionImpl() {
        countForElement = new HashMap<>();
    }

    public CountableCollectionImpl(int initialCapacity) {
        countForElement = new HashMap<>(initialCapacity);
    }

    public void add(T element, int count) {
        Verifiers.verify(count > 0, "Failed to add zero count of elements");

        if (!countForElement.containsKey(element)) {
            countForElement.put(element, count);
        }
        else {
            Integer currentCount = countForElement.get(element);
            countForElement.put(element, currentCount + count);
        }
    }

    public void remove(T element, int count) {
        Verifiers.verify(count > 0, "Failed to remove zero count of elements");

        Integer currentCount = countForElement.get(element);
        Verifiers.verify(currentCount >= count, "Failed to remove specified count of element: %d", count);

        currentCount -= count;
        if (currentCount > 0) {
            countForElement.put(element, currentCount);
        }
        else {
            countForElement.remove(element);
        }
    }

    public int count(T element) {
        if (countForElement.containsKey(element)) {
            return countForElement.get(element);
        }
        return 0;
    }

    public long size() {
        return countForElement.size();
    }

    @Override
    public Iterator<T> iterator() {
        return countForElement.keySet().iterator();
    }
}
