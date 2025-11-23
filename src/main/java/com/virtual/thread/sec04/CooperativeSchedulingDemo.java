package com.virtual.thread.sec04;

import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class CooperativeSchedulingDemo {

    private static final Logger log = LoggerFactory.getLogger(CooperativeSchedulingDemo.class);

    static {
        System.setProperty("jdk.virtualThreadScheduler.parallelism", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
    }

    static void main() {
        log.info("Cooperative Scheduling Demo Started");
        Thread.Builder.OfVirtual builder = Thread.ofVirtual();
        List<Thread> threads = List.of(
            builder.unstarted(() -> demo(1)),
            builder.unstarted(() -> demo(2))
        );

        log.info("Starting selfish threads");
        threads.forEach(Thread::start);

        List<Thread> yieldThreads = List.of(
            builder.unstarted(() -> yieldDemo(1)),
            builder.unstarted(() -> yieldDemo(2))
        );

        CommonUtils.sleep(Duration.ofSeconds(2));

        log.info("Starting yielding threads");
        yieldThreads.forEach(Thread::start);

        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static void demo(int threadNumber) {
        log.info("thread={} started", threadNumber);

        for (int i = 0; i < 10; i++) {
            log.info("thread-{} is printing {}. Thread: {}", threadNumber, i, Thread.currentThread());
        }

        log.info("thread={} ended", threadNumber);
    }

    private static void yieldDemo(int threadNumber) {
        log.info("thread={} started", threadNumber);

        for (int i = 0; i < 10; i++) {
            log.info("thread-{} is printing {}. Thread: {}", threadNumber, i, Thread.currentThread());
            Thread.yield(); // Yield control to allow other threads to run
        }

        log.info("thread={} ended", threadNumber);
    }

}
