package com.virtual.thread.sec08_completableFuture;

import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class SimpleCompletableFuture {
    private static final Logger log = LoggerFactory.getLogger(SimpleCompletableFuture.class);

    static void main(String[] args) {
        log.info("main starts");

        CompletableFuture<String> cf = slowTask();
        cf.thenAccept(v -> log.info("value: {}", v));

        log.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<String> fastTask() {
        log.info("fast method starts");
        CompletableFuture<String> cf = new CompletableFuture<String>();
        cf.complete("hi");
        log.info("fast method ends");
        return cf;
    }

    private static CompletableFuture<String> slowTask() {
        log.info("slow method starts");
        CompletableFuture<String> cf = new CompletableFuture<String>();

        Thread.ofVirtual().start( () -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            cf.complete("hi");
        });

        log.info("slow method ends");
        return cf;
    }

}
