package core;

import java.util.HashMap;
import java.util.Map;

public class CountableCollectionImpl<T extends Comparable<T>> implements CountableCollection<T> {
    private final Map<T, Integer> elementsCount = new HashMap<T, Integer>();

    public void add(T element, int count) {
        Verifiers.verify(count > 0, "Failed to add zero count of elements");

        if (!elementsCount.containsKey(element)) {
            elementsCount.put(element, count);
        }
        else {
            Integer currentCount = elementsCount.get(element);
            elementsCount.put(element, currentCount + count);
        }
    }

    public void remove(T element, int count) {
        Verifiers.verify(count > 0, "Failed to remove zero count of elements");

        Integer currentCount = elementsCount.get(element);
        Verifiers.verify(currentCount >= count, "Failed to remove specified count of element: %d", count);

        currentCount -= count;
        if (currentCount > 0) {
            elementsCount.put(element, currentCount);
        }
        else {
            elementsCount.remove(element);
        }
    }

    public int count(T element) {
        if (elementsCount.containsKey(element)) {
            return elementsCount.get(element);
        }
        return 0;
    }

    public long size() {
        return elementsCount.size();
    }
}
