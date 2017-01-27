public class ConcurrentEventTracker implements EventTracker {

    private final TimeWatcher timeWatcher = new TimeWatcher();
    private final TimeIntervalNumberCalculator intervalNumberCalculator;

    public ConcurrentEventTracker(int timePrecisionNs) {
        intervalNumberCalculator = new TimeIntervalNumberCalculator(timePrecisionNs);
    }

    public void registerEvent() {
        long elapsedTime = timeWatcher.elapsedTime();
        long intervalNumber = intervalNumberCalculator.calculateIntervalNumber(elapsedTime);
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
