package com.virtual.thread.sec08_completableFuture;

import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class SupplyAsyncDemo {
    private static final Logger log = LoggerFactory.getLogger(SupplyAsyncDemo.class);

    static void main() {
        log.info("main starts");

        runAsync().thenAccept(v -> log.info("value={}", v));

        log.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<String> runAsync() {
        log.info("slow method starts");

        var cf = CompletableFuture.supplyAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            return "hi";
        }, Executors.newVirtualThreadPerTaskExecutor());

        log.info("slow method ends");
        return cf;
    }
}
