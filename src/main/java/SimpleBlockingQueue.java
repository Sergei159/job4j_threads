import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private final int limit = 2;

    public SimpleBlockingQueue() {
        this.queue = new LinkedList<>(queue);
    }

    public synchronized void offer(T value) {
        while (queue.size() == limit) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.notifyAll();
        queue.offer(value);

    }

    public synchronized T poll() {
        while (queue.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.notifyAll();
        return queue.poll();
    }

    public synchronized int size() {
        return queue.size();
    }
}