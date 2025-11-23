package com.virtual.thread.sec07;

import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExecutorServiceTypesDemo {
    private static final Logger log = LoggerFactory.getLogger(ExecutorServiceTypesDemo.class);

    static void main(String[] args) {
        //execute(Executors.newSingleThreadExecutor(), 3);
        //execute(Executors.newFixedThreadPool(5), 7);
        //execute(Executors.newCachedThreadPool(), 200);
        //execute(Executors.newVirtualThreadPerTaskExecutor(), 10_000);
        schedule();
    }

    private static void schedule() {
        try(ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor()) {
            executorService.scheduleAtFixedRate(() -> {
                log.info("executing task...");
            }, 0, 1, java.util.concurrent.TimeUnit.SECONDS);

            CommonUtils.sleep(Duration.ofSeconds(5));
        }
    }

    private static void execute(ExecutorService executorService, int taskCount) {
        try (executorService) {
            for (int i = 0; i < taskCount; i++) {
                final int taskId = i;
                executorService.submit(() -> ioTask(taskId));
            }
            log.info("All tasks submitted");
        }
    }

    private static void ioTask(int i) {
        log.info("Task started: {}. Thread info: {}", i, Thread.currentThread());
        CommonUtils.sleep(Duration.ofSeconds(5));
        log.info("Task end: {}. Thread info: {}", i, Thread.currentThread());
    }

}
