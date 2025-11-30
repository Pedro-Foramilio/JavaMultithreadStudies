package com.virtual.thread.sec08_completableFuture;

import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class RunAsyncDemo {
    private static final Logger log = LoggerFactory.getLogger(RunAsyncDemo.class);

    static void main() {
        log.info("main starts");

        runAsync()
                .thenRun(() -> log.info("it is all done"))
                .exceptionally(ex -> {
                    log.error("Exception occurred: {}", ex.getMessage());
                    return null;
                });

        log.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<Void> runAsync() {
        log.info("slow method starts");

        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
           CommonUtils.sleep(Duration.ofSeconds(1));
//           log.info("task completed");
            throw new RuntimeException("oops! something went wrong");
        }, Executors.newVirtualThreadPerTaskExecutor());

        log.info("slow method ends");
        return cf;
    }
}
