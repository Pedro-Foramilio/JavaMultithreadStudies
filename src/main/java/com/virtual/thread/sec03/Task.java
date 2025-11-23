package com.virtual.thread.sec03;

import com.virtual.thread.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {
    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void cpuIntensiveTask(int i) {
        //log.info("CPU Intensive Task started. Thread info {}", Thread.currentThread());
        long duration = CommonUtils.timer(() -> fib(i));
        //log.info("CPU Intensive Task completed in {} ms", duration);
    }

    /// bad fib  implementation
    public static long fib(long input) {
        if (input <2) return input;

        return fib(input - 1) + fib(input - 2);
    }
}
