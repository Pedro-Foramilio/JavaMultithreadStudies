package com.virtual.thread.sec03;

import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class CpuIntensiveTaskDemo {

    private static final Logger log = LoggerFactory.getLogger(CpuIntensiveTaskDemo.class);
    private final static int TASK_COUNT = 2 * Runtime.getRuntime().availableProcessors();

    static void main(String[] args) {

        log.info("Tasks count {}", TASK_COUNT);

        for (int i = 0; i < 3; i++) {
            long totalTimeTaken = CommonUtils.timer(() -> demo(Thread.ofVirtual()));
            log.info("Total time taken in virtual threads run {} ms", totalTimeTaken);

            totalTimeTaken = CommonUtils.timer(() -> demo(Thread.ofPlatform()));
            log.info("Total time taken in platform threads run {} ms", totalTimeTaken);
        }
    }

    private static void demo(Thread.Builder threadBuilder) {
        CountDownLatch latch = new CountDownLatch(TASK_COUNT);
        for (int i = 0; i < TASK_COUNT; i++) {
            threadBuilder.start(() -> {
                Task.cpuIntensiveTask(45);
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
