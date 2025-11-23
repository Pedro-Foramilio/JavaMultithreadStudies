package com.virtual.thread.sec08;

import com.virtual.thread.sec08.aggregator.AggregatorService;
import com.virtual.thread.sec08.aggregator.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AggregatorDemo {
    private static final Logger log = LoggerFactory.getLogger(AggregatorDemo.class);

    static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        AggregatorService aggregatorService = new AggregatorService(executor);

        List<Future<ProductDto>> futures = IntStream.rangeClosed(1, 50)
                        .mapToObj( id ->
                                executor.submit(
                                        () -> aggregatorService.getProductDetails(id)
                                )).toList();

        List<ProductDto> products = futures.stream().map(AggregatorDemo::toProductDto).toList();

        log.info("list: {}", products);
    }

    private static ProductDto toProductDto(Future<ProductDto> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
