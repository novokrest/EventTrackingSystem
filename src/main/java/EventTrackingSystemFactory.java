import core.MultiBlockingQueue;
import core.Verifiers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EventTrackingSystemFactory {
    private final EventRegisterer eventRegisterer;
    private final EventConsumers eventConsumers;
    private final EventRegistry eventRegistry;

    public static EventTrackingSystemFactory createDefault(int intervalsCountMax) {
        int processors = Runtime.getRuntime().availableProcessors();
        processors = processors > 0 ? processors : 1;

        int queuesCount = processors / 2;
        queuesCount = queuesCount > 0 ? queuesCount : 1;

        return new EventTrackingSystemFactory(intervalsCountMax, processors, queuesCount, Integer.MAX_VALUE);
    }

    public EventTrackingSystemFactory(int intervalsCountMax, int workersCount, int queuesCount, int queueSize) {
        Verifiers.verifyArg(intervalsCountMax > 0, "intervalsCountMax", queuesCount);
        Verifiers.verifyArg(workersCount > 0, "workersCount", queuesCount);
        Verifiers.verifyArg(queuesCount > 0, "queuesCount", queuesCount);
        Verifiers.verifyArg(queueSize > 0, "queuesSize", queueSize);
        Verifiers.verify(workersCount >= queuesCount, "Workers count must be equal or greater to queues count");

        List<BlockingQueue<Long>> queues = createQueues(queuesCount, queueSize);
        eventRegistry = createEventRegistry(intervalsCountMax);
        eventConsumers = createEventConsumers(workersCount, queues, eventRegistry);
        eventRegisterer = createEventRegisterer(queues);

    }

    public EventRegisterer getEventRegisterer() {
        return eventRegisterer;
    }

    public EventConsumers getEventConsumers() {
        return eventConsumers;
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    private static List<BlockingQueue<Long>> createQueues(int queuesCount, int queueSize) {
        ArrayList<BlockingQueue<Long>> queues = new ArrayList<>(queuesCount);
        for (int i = 0; i < queuesCount; ++i) {
            queues.add(new LinkedBlockingQueue<>(queueSize));
        }
        return queues;
    }

    private static EventRegistry createEventRegistry(int intervalsCountMax) {
        return new EventRegistryImpl(intervalsCountMax);
    }

    private static EventConsumers createEventConsumers(int consumersCount, List<BlockingQueue<Long>> queues,
                                                       EventRegistry registry) {
        return EventConsumers.create(consumersCount, queues, registry);
    }

    private static EventRegisterer createEventRegisterer(List<BlockingQueue<Long>> queues) {
        return new EventRegisterer(new MultiBlockingQueue<>(queues));
    }
}
