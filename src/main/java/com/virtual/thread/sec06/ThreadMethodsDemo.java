package com.virtual.thread.sec06;

import com.virtual.thread.sec05.SyncWithLock;
import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ThreadMethodsDemo {
    private static final Logger log = LoggerFactory.getLogger(SyncWithLock.class);

    static void main(String[] args) throws InterruptedException {
        //isVirtual();
        //join();
        interrupt();
    }

    private static void isVirtual() {
        Thread t1 = Thread.ofVirtual().unstarted(() -> {
            log.info("Virtual Thread");
        });

        Thread t2 = Thread.ofPlatform().unstarted(() -> {
            log.info("Platform Thread");
        });

        t1.start();
        t2.start();

        log.info("Is t1 virtual? {}", t1.isVirtual());
        log.info("Is t2 virtual? {}", t2.isVirtual());
        log.info("Is current thread virtual? {}", Thread.currentThread().isVirtual());
    }

    private static void join() throws InterruptedException {
        Thread t1 = Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(5));
            log.info("Task in t1 completed.");
        });
        Thread t2 = Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            log.info("Task in t2 completed.");
        });

        t1.join(); // waits for t1 to finish
        t2.join();
        /// vithout joins the application exists immediatly since virtual threads are deamon threads
    }

    private static void interrupt() {
        Thread t1 = Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(5));
            log.info("Task in t1 completed.");
        });

        log.info("is t1 interrupted? {}", t1.isInterrupted());
        t1.interrupt();
        log.info("is t1 interrupted? {}", t1.isInterrupted());
    }

}
