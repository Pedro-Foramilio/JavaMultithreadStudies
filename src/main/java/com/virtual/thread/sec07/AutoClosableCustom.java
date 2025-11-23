package com.virtual.thread.sec07;

import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AutoClosableCustom {

    private static final Logger log = LoggerFactory.getLogger(AutoClosableCustom.class);

    static void main(String[] args) {
        ExecutorService executerService = Executors.newSingleThreadExecutor();
        executerService.submit(AutoClosableCustom::task);
        log.info("Submitted");
        executerService.shutdown();
        /// forces running tasks to stop
        //executerService.shutdownNow();

        /* equivalent to above
        try(ExecutorService executerService = Executors.newSingleThreadExecutor()) {
            executerService.submit(AutoClosable::task);
            log.info("Submitted");
        }
         */
    }

    private static void task() {
        CommonUtils.sleep(Duration.ofSeconds(2));
        log.info("task executed");
    }

    public abstract void close() throws Exception;
}
