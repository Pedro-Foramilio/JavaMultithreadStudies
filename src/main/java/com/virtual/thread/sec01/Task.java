package com.virtual.thread.sec01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {
    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    public static void ioIntesive(int i) {
        try {
            logger.info("starting I/O intensive task {}. Thread info {}", i, Thread.currentThread());
            Thread.sleep(Duration.ofSeconds(10));
            logger.info("ending I/O intensive task {}. Thread info {}", i, Thread.currentThread());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
