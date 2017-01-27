import core.CountableCollection;
import core.CountableCollectionImpl;
import core.Verifiers;
import exceptions.FailedEventTrackingStopExecption;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class EventConsumers {
    private final List<EventConsumer> eventConsumers;
    private final EventConsumersStopper stopper;
    private List<Thread> workers;

    public static EventConsumers create(int consumersCount, List<BlockingQueue<Long>> queues, EventRegistry registry) {
        List<EventConsumer> eventConsumers = new ArrayList<>(consumersCount);
        CountableCollection<BlockingQueue<Long>> consumersCountForQueue = new CountableCollectionImpl<>(queues.size());

        createEventConsumers(consumersCount, queues, registry, eventConsumers, consumersCountForQueue);
        EventConsumersStopper stopper = new EventConsumersStopper(consumersCountForQueue);

        return new EventConsumers(eventConsumers, stopper);
    }

    private static void createEventConsumers(int consumersCount, List<BlockingQueue<Long>> queues,
                                             EventRegistry registry,
                                             List<EventConsumer> eventConsumers,
                                             CountableCollection<BlockingQueue<Long>> consumersCountForQueue ) {
        for (int i = 0; i < consumersCount; ++i) {
            BlockingQueue<Long> queue = queues.get(i % queues.size());
            eventConsumers.add(createEventConsumer(queue, registry));
            consumersCountForQueue.add(queue, 1);
        }
    }

    private static EventConsumer createEventConsumer(BlockingQueue<Long> queue, EventRegistry registry) {
        EventTimeProvider provider = new EventTimeProvider(queue);
        EventHandler handler = new EventHandler(registry);
        return new EventConsumer(provider, handler);
    }

    private EventConsumers(List<EventConsumer> eventConsumers, EventConsumersStopper stopper) {
        this.eventConsumers = eventConsumers;
        this.stopper = stopper;
    }

    public void start() {
        Verifiers.verify(workers == null, "Event tracking has been already started");

        workers = new ArrayList<>(eventConsumers.size());
        for (EventConsumer consumer : eventConsumers) {
            Thread workerThread = new Thread(consumer);
            workerThread.start();
            workers.add(workerThread);
        }
    }

    public void stop() {
        try {
            stopper.stop();

            for (Thread worker : workers) {
                worker.join();
            }
            workers = null;
        } catch (InterruptedException e) {
            throw new FailedEventTrackingStopExecption(e);
        }
    }
}
