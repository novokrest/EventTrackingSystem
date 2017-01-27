import core.WellKnownTimePeriod;
import exceptions.FailedEventRegistrationException;

public class ConcurrentEventTracker implements EventTracker {
    private final TimeWatcher timeWatcher = new TimeWatcher();
    private final TimeIntervalNumberCalculator intervalNumberCalculator;
    private final EventRegisterer eventRegisterer;
    private final EventConsumers eventConsumers;


    public ConcurrentEventTracker() {
        this(WellKnownTimePeriod.ONE_SECOND.dividedBy(100).getNano());
    }

    public ConcurrentEventTracker(int timePrecisionNs) {
        this(timePrecisionNs, EventTrackingSystemFactory.createDefault());
    }

    private ConcurrentEventTracker(int timePrecisionNs, EventTrackingSystemFactory factory) {
        intervalNumberCalculator = new TimeIntervalNumberCalculator(timePrecisionNs);
        eventRegisterer = factory.getEventRegisterer();
        eventConsumers = factory.getEventConsumers();
    }

    public void start() {
        eventConsumers.start();
    }

    public void stop() {
        eventConsumers.stop();
    }

    public void registerEvent() {
        long elapsedTime = timeWatcher.elapsedTime();
        long intervalNumber = intervalNumberCalculator.calculateIntervalNumber(elapsedTime);
        try {
            eventRegisterer.registerEvent(intervalNumber);
        } catch (InterruptedException e) {
            throw new FailedEventRegistrationException(e);
        }
    }

    public long getLastMinuteEventsCount() {
        return 0;
    }

    public long getLastHourEventsCount() {
        return 0;
    }

    public long getLastDayEventsCount() {
        return 0;
    }
}
