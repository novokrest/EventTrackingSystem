public class EventConsumer implements Runnable {
    public static final long STOP_EVENT_TIME = -1;

    private final EventTimeProvider eventTimeProvider;
    private final EventHandler eventHandler;

    public EventConsumer(EventTimeProvider eventTimeProvider, EventHandler eventHandler) {
        this.eventTimeProvider = eventTimeProvider;
        this.eventHandler = eventHandler;
    }

    @Override
    public void run() {
        boolean waitForNext = true;
        while(waitForNext){
            TakeNextEventResult result = takeNextEvent();
            if (result.isSuccess()) {
                handleEvent(result.getEventTime());
            }
            waitForNext = result.isSuccess();
        }
        System.out.println(String.format("%d stopped", Thread.currentThread().getId()));
    }

    private TakeNextEventResult takeNextEvent() {
        try {
            long eventTime = eventTimeProvider.takeNextEventTime();
            if (eventTime == STOP_EVENT_TIME) {
                return TakeNextEventResult.fail();
            }
            return TakeNextEventResult.success(eventTime);
        } catch (InterruptedException e) {
            // TODO: log message
            return TakeNextEventResult.fail();
        }
    }

    private void handleEvent(long eventTime) {
        eventHandler.handleEvent(eventTime);
    }

    private static class TakeNextEventResult {
        private final boolean success;
        private final long eventTime;

        private static TakeNextEventResult success(long eventTime) {
            return new TakeNextEventResult(true, eventTime);
        }

        public static TakeNextEventResult fail() {
            return new TakeNextEventResult(false, -1);
        }

        private TakeNextEventResult(boolean success, long eventTime) {
            this.success = success;
            this.eventTime = eventTime;
        }

        public boolean isSuccess() {
            return success;
        }

        public long getEventTime() {
            return eventTime;
        }
    }
}
