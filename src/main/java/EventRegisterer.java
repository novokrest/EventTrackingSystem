import core.MultiBlockingQueue;

public class EventRegisterer {
    private final MultiBlockingQueue<Long> eventTimes;

    public EventRegisterer(MultiBlockingQueue<Long> eventTimes) {
        this.eventTimes = eventTimes;
    }

    public void registerEvent(long eventTime) throws InterruptedException {
        eventTimes.put(eventTime);
    }
}
