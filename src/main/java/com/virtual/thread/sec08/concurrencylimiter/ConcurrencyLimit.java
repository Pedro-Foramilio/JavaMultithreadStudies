package com.virtual.thread.sec08.concurrencylimiter;

import com.virtual.thread.sec08.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class ConcurrencyLimit {
    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimit.class);

    static void main(String[] args) throws Exception {
        ConcurrencyLimiter limiter = new ConcurrencyLimiter(Executors.newVirtualThreadPerTaskExecutor(), 3);
        execute(limiter, 20);
    }

    private static void execute(ConcurrencyLimiter concurrencyLimiter, int taskCount) throws Exception {
        try(concurrencyLimiter) {
            for (int i = 0; i < taskCount; i++) {
                int j = i;
                concurrencyLimiter.submit(() -> print(j));
            }
            log.info("submitted all tasks");
        }
    }

    private static String print(int id) {
        String product = Client.getProduct(id);
        log.info("{} => {}", id, Client.getProduct(id));
        return product;
    }

}
