import java.util.concurrent.BlockingQueue;

public class EventTimeProvider {
    private final BlockingQueue<Long> _eventTimes;

    public EventTimeProvider(BlockingQueue<Long> eventTimes) {
        _eventTimes = eventTimes;
    }

    public long takeNextEventTime() throws InterruptedException {
        return _eventTimes.take();
    }
}
