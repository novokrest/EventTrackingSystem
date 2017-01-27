public interface EventTracker {
    void registerEvent();
    long getLastMinuteEventsCount();
    long getLastHourEventsCount();
    long getLastDayEventsCount();
}
