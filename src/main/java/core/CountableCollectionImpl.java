package core;

import java.util.HashMap;
import java.util.Map;

public class CountableCollectionImpl<T extends Comparable<T>> implements CountableCollection<T> {
    private final Map<T, Integer> elementCount = new HashMap<T, Integer>();

    public void add(T element, int count) {
        Verifiers.verify(count > 0, "Failed to add zero count of elements");

        if (!elementCount.containsKey(element)) {
            elementCount.put(element, count);
        }
        else {
            Integer currentCount = elementCount.get(element);
            elementCount.put(element, currentCount + count);
        }
    }

    public void remove(T element, int count) {
        Verifiers.verify(count > 0, "Failed to remove zero count of elements");

        Integer currentCount = elementCount.get(element);
        Verifiers.verify(currentCount >= count, "Failed to remove specified count of element: %d", count);

        currentCount -= count;
        if (currentCount > 0) {
            elementCount.put(element, currentCount);
        }
        else {
            elementCount.remove(element);
        }
    }

    public int count(T element) {
        if (elementCount.containsKey(element)) {
            return elementCount.get(element);
        }
        return 0;
    }

    public long size() {
        return elementCount.size();
    }
}
