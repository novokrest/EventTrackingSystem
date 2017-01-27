public interface EventRegistry {
    void registerEvent(long eventTime);
    long getEventsCountAppearedFrom(long eventTime);
}
