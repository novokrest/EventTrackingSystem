import core.WellKnownTimePeriod;
import exceptions.FailedEventRegistrationException;

import java.time.Duration;

public class ConcurrentEventTracker implements EventTracker {
    private final Object lock = new Object();

    private final TimeWatcher timeWatcher = new TimeWatcher();
    private final TimeIntervalNumberCalculator intervalNumberCalculator;
    private final EventRegisterer eventRegisterer;
    private final EventConsumers eventConsumers;
    private final EventRegistry eventRegistry;

    private int registerEventProcessing;
    private int getEventsCountProcessing;


    public ConcurrentEventTracker() {
        this(WellKnownTimePeriod.ONE_SECOND.dividedBy(10).toNanos());
    }

    public ConcurrentEventTracker(long timePrecisionNs) {
        this(timePrecisionNs, WellKnownTimePeriod.ONE_DAY.toNanos() / timePrecisionNs + 1);
    }

    public ConcurrentEventTracker(long timePrecisionMs, long intervalsCountMax) {
        this(timePrecisionMs, EventTrackingSystemFactory.createDefault(intervalsCountMax));
    }

    private ConcurrentEventTracker(long timePrecisionMs, EventTrackingSystemFactory factory) {
        intervalNumberCalculator = new TimeIntervalNumberCalculator(timePrecisionMs);
        eventRegisterer = factory.getEventRegisterer();
        eventConsumers = factory.getEventConsumers();
        eventRegistry = factory.getEventRegistry();
    }

    public void start() {
        eventConsumers.start();
    }

    public void stop() {
        eventConsumers.stop();
    }

    public void registerEvent() {
        startRegisterEventProcessing();

        try {
            long elapsedTime = timeWatcher.elapsedTimeNs();
            long intervalNumber = intervalNumberCalculator.calculateIntervalNumber(elapsedTime);
            eventRegisterer.registerEvent(intervalNumber);
        } catch (InterruptedException e) {
            throw new FailedEventRegistrationException(e);
        } finally {
            stopRegisterEventProcessing();
        }
    }

    private void startRegisterEventProcessing() {
        synchronized (lock) {
            while (getEventsCountProcessing > 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    // TODO: log exception
                }
            }
            ++registerEventProcessing;
        }
    }

    private void stopRegisterEventProcessing() {
        synchronized (lock) {
            --registerEventProcessing;
            lock.notifyAll();
        }
    }

    public long getLastMinuteEventsCount() {
        return getEventsCount(WellKnownTimePeriod.ONE_MINUTE);
    }

    public long getLastHourEventsCount() {
        return getEventsCount(WellKnownTimePeriod.ONE_HOUR);
    }

    public long getLastDayEventsCount() {
        return getEventsCount(WellKnownTimePeriod.ONE_DAY);
    }

    private long getEventsCount(Duration period) {
        startGetEventsCountProcessing();

        try {
            long startTime = timeWatcher.elapsedTimeNs() - period.getNano();
            long startInterval = intervalNumberCalculator.calculateIntervalNumber(startTime);
            return eventRegistry.getEventsCountAppearedFrom(startInterval);
        } finally {
          stopGetEventsCountProcessing();
        }
    }

    private void startGetEventsCountProcessing() {
        synchronized (lock) {
            while (registerEventProcessing > 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    // TODO: log exception
                }
            }
            ++getEventsCountProcessing;
        }
    }

    private void stopGetEventsCountProcessing() {
        synchronized (lock) {
            --getEventsCountProcessing;
            lock.notifyAll();
        }
    }
}
