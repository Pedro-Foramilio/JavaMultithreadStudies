package com.virtual.thread.sec08.concurrencylimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;

public class ConcurrencyLimiter implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimiter.class);

    private final ExecutorService executor;
    private final Semaphore semaphore;
    /// requirement if problem requirest orderd task execution
    private final Queue<Callable<?>> taskQueue;

    public ConcurrencyLimiter(ExecutorService executor, int maxConcurrentTasks) {
        this.executor = executor;
        this.semaphore = new Semaphore(maxConcurrentTasks);
        this.taskQueue = new ConcurrentLinkedDeque<>();
    }

    public <T> Future<T> submit(Callable<T> callable) {
        this.taskQueue.add(callable);
        return executor.submit(() -> executeTask());
    }

    private <T> T executeTask() {
        try {
            semaphore.acquire();
            return (T) this.taskQueue.poll().call();
        } catch (Exception e) {
            log.error("Error executing task", e);
        } finally {
            semaphore.release();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        this.executor.close();
    }

}
