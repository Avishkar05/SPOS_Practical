import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {
    private final int[] buffer;
    private int in = 0, out = 0;
    private final Semaphore empty;
    private final Semaphore full;
    private final ReentrantLock mutex;

    public BoundedBuffer(int size) {
        buffer = new int[size];
        empty = new Semaphore(size);
        full = new Semaphore(0);
        mutex = new ReentrantLock();
    }

    public void produce(int item, int producerId) throws InterruptedException {
        empty.acquire();
        mutex.lock();
        try {
            buffer[in] = item;
            System.out.println("Producer " + producerId + " produced " + item);
            in = (in + 1) % buffer.length;
        } finally {
            mutex.unlock();
        }
        full.release();
    }

    public int consume(int consumerId) throws InterruptedException {
        full.acquire();
        mutex.lock();
        int item;
        try {
            item = buffer[out];
            System.out.println("Consumer " + consumerId + " consumed " + item);
            out = (out + 1) % buffer.length;
        } finally {
            mutex.unlock();
        }
        empty.release();
        return item;
    }
}

class Producer extends Thread {
    private final BoundedBuffer buffer;
    private final int id;
    public Producer(BoundedBuffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                buffer.produce(id * 100 + i, id);
                Thread.sleep((int)(Math.random() * 200));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer extends Thread {
    private final BoundedBuffer buffer;
    private final int id;
    public Consumer(BoundedBuffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                buffer.consume(id);
                Thread.sleep((int)(Math.random() * 250));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class BoundedBufferMain {
    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer(5);
        for (int i = 0; i < 2; i++) new Producer(buffer, i).start();
        for (int i = 0; i < 2; i++) new Consumer(buffer, i).start();
    }
}

