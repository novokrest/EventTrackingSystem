
public class EventHandler {
    private final EventRegistry registry;

    public EventHandler(EventRegistry registry) {
        this.registry = registry;
    }

    public void handleEvent(long eventTime) {
        registry.registerEvent(eventTime);
    }
}
