package ru.job4j.buffer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private int limit = 2;

    public SimpleBlockingQueue() {
        this.queue = new LinkedList<>(queue);
        limit = Integer.MAX_VALUE;
    }

    public SimpleBlockingQueue(Queue<T> queue, int limit) {
        this.queue = queue;
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
                this.wait();
        }
        this.notifyAll();
        queue.offer(value);

    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
                this.wait();
        }
        this.notifyAll();
        return queue.poll();
    }

    public synchronized int size() {
        return queue.size();
    }
}