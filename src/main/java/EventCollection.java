import core.AtomicValue;
import core.AvlTree;

public class EventCollection {
    private final AvlTree<Long, AtomicValue> tree = new AvlTree<>();
    private final long maxSize;

    public EventCollection(long maxSize) {
        this.maxSize = maxSize;
    }

    public void add(long eventTime) {
        if (tree.foundValue(eventTime) != null) {
            tree.increment(eventTime);
            return;
        }

        tree.insert(eventTime, new AtomicValue(1));
        if (tree.size() > maxSize) {
            tree.removeMin();
        }
    }

    public long getTotalFrom(long eventTime) {
        if (tree.empty()) {
            return 0;
        }

        AtomicValue totalEvents = tree.value();
        AtomicValue beforeEvents = tree.foundValue(eventTime - 1);

        if (beforeEvents == null) {
            return totalEvents.getInt();
        }

        return totalEvents.getInt() - beforeEvents.getInt();
    }
}
