package core;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class MultiBlockingQueue<E> {
    private final Random random = new Random();
    private final List<BlockingQueue<E>> queues;

    public MultiBlockingQueue(List<BlockingQueue<E>> queues) {
        this.queues = queues;
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
