package com.virtual.thread.util;

import java.time.Duration;

public class CommonUtils {

    public static void sleep(Duration durantion) {
        try {
            Thread.sleep(durantion);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static long timer(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        return (end - start);
    }
}
