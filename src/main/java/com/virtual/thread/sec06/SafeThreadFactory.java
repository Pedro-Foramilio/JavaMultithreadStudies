package com.virtual.thread.sec06;

import com.virtual.thread.sec05.SyncWithLock;
import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;

public class SafeThreadFactory {

    private static final Logger log = LoggerFactory.getLogger(SyncWithLock.class);

    static void main(String[] args) {
        demo(Thread.ofVirtual().factory());

        CommonUtils.sleep(Duration.ofSeconds(10));
    }

    private static void demo(ThreadFactory factory) {
        for (int i = 0; i < 3; i++) {

            Thread t = factory.newThread(() -> {
                log.info("Task started. {}", Thread.currentThread());
                Thread subThread = factory.newThread(() -> {
                    log.info("Child Task started. {}", Thread.currentThread());
                    CommonUtils.sleep(Duration.ofSeconds(2));
                    log.info("Child Task ended. {}", Thread.currentThread());
                });
                subThread.start();
                log.info("Task ended. {}", Thread.currentThread());
            });
            t.start();
        }
    }

}
