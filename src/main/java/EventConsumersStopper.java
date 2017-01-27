import core.CountableCollection;

import java.util.concurrent.BlockingQueue;

public class EventConsumersStopper {
    private final CountableCollection<BlockingQueue<Long>> consumersCountForQueue;

    public EventConsumersStopper(CountableCollection<BlockingQueue<Long>> queueToConsumersCount) {
        this.consumersCountForQueue = queueToConsumersCount;
    }

    public void stop() throws InterruptedException {
        for (BlockingQueue<Long> queue: consumersCountForQueue) {
            int consumersCount = consumersCountForQueue.count(queue);
            stopConsumers(queue, consumersCount);
        }
    }

    private void stopConsumers(BlockingQueue<Long> queue, int consumersCount) throws InterruptedException {
        for (int i = 0; i < consumersCount; ++i) {
            queue.put(EventConsumer.STOP_EVENT_TIME);
        }
    }
}
