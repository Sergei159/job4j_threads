import org.junit.Test;
import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenPollFromQueue() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(
                new LinkedList<>(), 2
        );
        Thread first =  new Thread(
                () -> {
                    IntStream.range(0, 3).forEach(
                            value -> {
                                try {
                                    blockingQueue.offer(value);
                                }  catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
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
                    IntStream.range(0, 3).forEach(
                            value -> {
                                try {
                                    blockingQueue.offer(value);
                                }  catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(blockingQueue.size(), is(2));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            value -> {
                                try {
                                    queue.offer(value);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (queue.size() != 0 || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

}