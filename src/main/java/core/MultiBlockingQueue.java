package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MultiBlockingQueue<E> {
    private final Random random = new Random();
    private final List<BlockingQueue<E>> queues;

    public MultiBlockingQueue(int queuesCount) {
        this(queuesCount, Integer.MAX_VALUE);
    }

    public MultiBlockingQueue(int queuesCount, int queueSize) {
        Verifiers.verifyArg(queuesCount > 0, "queuesCount", queuesCount);
        Verifiers.verifyArg(queueSize > 0, "queuesSize", queueSize);

        queues = new ArrayList<>(queuesCount);
        AddQueues(queueSize);
    }

    private void AddQueues(final int queueSize) {
        for (int i = 0; i < queueSize; ++i) {
            queues.add(new LinkedBlockingQueue<E>(queueSize));
        }
    }

    public void put(E element) throws InterruptedException {
        int position = getRandomQueuePosition();
        for (BlockingQueue<E> queue : ListEx.toCircular(queues, position)) {
            if (queue.remainingCapacity() > 0) {
                queue.put(element);
                return;
            }
        }
        queues.get(position).put(element);
    }

    public E take() throws InterruptedException {
        int position = getRandomQueuePosition();
        for (BlockingQueue<E> queue : ListEx.toCircular(queues, position)) {
            if (!queue.isEmpty()) {
                return queue.take();
            }
        }
        return queues.get(position).take();
    }

    private int getRandomQueuePosition() {
        return random.nextInt(queues.size());
    }
}
