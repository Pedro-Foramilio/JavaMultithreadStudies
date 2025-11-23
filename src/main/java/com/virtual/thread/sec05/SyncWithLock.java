package com.virtual.thread.sec05;

import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/// How work arround Pinning Threads
public class SyncWithLock {

    private static final Logger log = LoggerFactory.getLogger(SyncWithLock.class);
    private static final Lock lock = new ReentrantLock();

    static void main(String[] args) {

        Runnable runnable = () -> log.info("*** teste message *** {}", Thread.currentThread());

        demo(Thread.ofVirtual());
        Thread.ofVirtual().start(runnable);
        CommonUtils.sleep(Duration.ofSeconds(15));
    }

    private static void demo(Thread.Builder threadBuilder) {
        for (int i = 0; i < 50; i++) {
            threadBuilder.start(() -> {
               log.info("Task started. {}", Thread.currentThread());
               IOtask();
               log.info("Task ended. {}", Thread.currentThread());
            });
        }
    }

    private static void IOtask() {
        try {
            lock.lock();
            CommonUtils.sleep(Duration.ofSeconds(10));
        } catch (Exception e) {
            log.error("Error occurred", e);
        } finally {
            lock.unlock();
        }
    }

}
