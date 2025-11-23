package com.virtual.thread.sec08.schedule;

import com.virtual.thread.sec08.externalservice.Client;
import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduleWithVirtual {
    private static final Logger log = LoggerFactory.getLogger(ScheduleWithVirtual.class);

    private static volatile AtomicInteger currentId = new AtomicInteger(1);

    static void main(String[] args) {
        schedule();
    }

    private static void schedule() {

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        try(scheduler; executor) {
            scheduler.scheduleAtFixedRate(() -> {
                executor.submit(() -> print(currentId.getAndAdd(1)));
            }, 0, 1, java.util.concurrent.TimeUnit.SECONDS);

            CommonUtils.sleep(Duration.ofSeconds(60));
        }
    }

    private static String print(int id) {
        String product = Client.getProduct(id);
        log.info("{} => {}", id, Client.getProduct(id));
        return product;
    }
}
