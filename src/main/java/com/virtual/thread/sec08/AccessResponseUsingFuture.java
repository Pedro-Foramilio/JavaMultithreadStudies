package com.virtual.thread.sec08;

import com.virtual.thread.sec08.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class AccessResponseUsingFuture {
    private static final Logger log = LoggerFactory.getLogger(AccessResponseUsingFuture.class);

    static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> product1 = executor.submit(() -> Client.getProduct(1));
            Future<String> product2 = executor.submit(() -> Client.getProduct(2));
            Future<String> product3 = executor.submit(() -> Client.getProduct(3));
            log.info("Product 1: {}", product1.get(2, TimeUnit.SECONDS));
            log.info("Product 2: {}", product2.get(2, TimeUnit.SECONDS));
            log.info("Product 3: {}", product3.get(1, TimeUnit.SECONDS));
        }
    }

}
