public class EventRegistryImpl implements EventRegistry {
    private final Object lock = new Object();
    private final EventCollection eventCollection;

    public EventRegistryImpl(long intervalsCountMax) {
        eventCollection = new EventCollection(intervalsCountMax);
    }

    @Override
    public void registerEvent(long eventTime) {
        synchronized (lock) {
            eventCollection.add(eventTime);
        }
    }

    @Override
    public long getEventsCountAppearedFrom(long eventTime) {
        return eventCollection.getTotalFrom(eventTime);
    }
}
