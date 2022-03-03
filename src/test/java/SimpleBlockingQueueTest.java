import org.junit.Test;
import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.Queue;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenPollFromQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(
                new LinkedList<>(), 2
        );
        Thread first = new Thread(
                () -> {
                    try {
                        blockingQueue.offer(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    try {
                        blockingQueue.offer(2);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    try {
                        blockingQueue.offer(3);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    try {
                        blockingQueue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        blockingQueue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(
                new LinkedList<>(), 2
        );
        Thread first = new Thread(
                () -> {
                    try {
                        blockingQueue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    try {
                        blockingQueue.offer(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    try {
                        blockingQueue.offer(2);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    try {
                        blockingQueue.offer(3);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(blockingQueue.size(), is(2));
    }

}