package com.virtual.thread.sec08_completableFuture;

import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class AggregatorsDemo {
    private static final Logger log = LoggerFactory.getLogger(AggregatorsDemo.class);


    static void main() {
        List<CompletableFuture<String>> futures = IntStream.rangeClosed(1, 50)
                .mapToObj(id -> AggregatorsDemo.someTask(id)
                        .exceptionally(ex -> "Task-" + id + " failed: " + ex.getMessage()))
                .toList();

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        List<String> results = futures.stream()
                .map(CompletableFuture::join)
                .toList();
        results.forEach(r -> log.info("{}", r));

        log.info("then combine demo");
        List<CompletableFuture<String>> futures2 = IntStream.rangeClosed(1, 50)
                .mapToObj(id -> AggregatorsDemo.someTask(id)
                        .exceptionally(ex -> "Task-" + id + " failed: " + ex.getMessage()))
                .toList();

        CompletableFuture<String> resultFuture = futures2.stream()
                .reduce((f1, f2) ->
                            f1.thenCombine(f2, (r1, r2) -> r1 + " | " + r2)
                ).orElseGet(() -> CompletableFuture.completedFuture("No tasks"));
        log.info(resultFuture.join());

    }

    private static CompletableFuture<String> someTask(int num) {
        return CompletableFuture.supplyAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(1));
            if (num % 10 == 0) {
                throw new RuntimeException("Simulated error in Task-" + num);
            }
            return "Result of Task-" + num;
        }, Executors.newVirtualThreadPerTaskExecutor());
    }

}
