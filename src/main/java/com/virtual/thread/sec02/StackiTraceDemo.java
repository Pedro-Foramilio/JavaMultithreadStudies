package com.virtual.thread.sec02;

import com.virtual.thread.util.CommonUtils;

import java.time.Duration;

public class StackiTraceDemo {

    static void main(String[] args) {
        demo(Thread.ofVirtual());
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static void demo(Thread.Builder builder) {
        for (int i = 0; i <= 20; i++) {
            int j = i;
            builder.start(() -> Task.execute(j));
        }
    }
}
