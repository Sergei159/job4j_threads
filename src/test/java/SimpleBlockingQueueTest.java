import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenPollFromQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>();
        Thread first = new Thread(
                () -> {
                    blockingQueue.offer(1);
                    blockingQueue.offer(2);
                    blockingQueue.offer(3);
                }
        );
        Thread second = new Thread(
                () -> {
                    blockingQueue.poll();
                    blockingQueue.poll();
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(blockingQueue.size(), is(1));
    }

    @Test
    public void whenOfferToQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>();
        Thread first = new Thread(
                blockingQueue::poll
        );
        Thread second = new Thread(
                () -> {
                    blockingQueue.offer(1);
                    blockingQueue.offer(2);
                    blockingQueue.offer(3);
                }
        );

        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(blockingQueue.size(), is(2));
    }

}