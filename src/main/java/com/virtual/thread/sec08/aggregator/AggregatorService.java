package com.virtual.thread.sec08.aggregator;

import com.virtual.thread.sec08.externalservice.Client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class AggregatorService {

    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public  ProductDto getProductDetails(int productId) throws ExecutionException, InterruptedException {
        Future<String> product = executorService.submit(() -> Client.getProduct(productId));
        Future<Integer> rating = executorService.submit(() -> Client.getRating(productId));
        return new ProductDto(productId, product.get(), rating.get());
    }
}
