package com.virtual.thread.sec08_completableFuture;

import com.virtual.thread.sec08.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetProducts {
    private static final Logger log = LoggerFactory.getLogger(GetProducts.class);

    static void main() throws ExecutionException, InterruptedException {
        try(ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor()) {
            CompletableFuture<String> p1 = CompletableFuture.supplyAsync( () -> Client.getProduct(1), exec);
            CompletableFuture<String> p2 = CompletableFuture.supplyAsync( () -> Client.getProduct(2), exec);
            CompletableFuture<String> p3 = CompletableFuture.supplyAsync( () -> Client.getProduct(3), exec);

            log.info("Products: {}, {}, {}", p1.get(), p2.get(), p3.get());
        }
    }

}
