package com.virtual.thread.sec01;

import java.util.concurrent.CountDownLatch;

public class InOutTaskDemo {

    private static final int MAX_PLATFORM = 10;
    private static final int MAX_VIRTUAL = 20;

    public static void main(String[] args) throws InterruptedException {
        //platformThreadDemo1();
        virtualThreadDemo();
    }

    private static void platformThreadDemo1() {
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = new Thread(
                    () -> Task.ioIntesive(j)
            );

            thread.start();
        }
    }

    private static void platformThreadDemo2() {

        Thread.Builder builder = Thread.ofPlatform().name("io-platform-thread-", 1);

        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(
                    ()    -> Task.ioIntesive(j)
            );
            thread.start();
        }
    }

    private static void platformThreadDemo3() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(MAX_PLATFORM);
        Thread.Builder builder = Thread.ofPlatform().daemon().name("daemon", 1);

        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(
                    () -> {
                        Task.ioIntesive(j);
                        latch.countDown();
                    }
            );
            thread.start();
        }
        latch.await();
    }

    /*
    * Virtual threads run on a fork join pool, executed by platform threads (carrier threads) handled by the jvm
    * Virtual threads has resizable stack memory
    *
    * */
    private static void virtualThreadDemo() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(MAX_PLATFORM);
        /// deamon by default
        Thread.Builder builder = Thread.ofVirtual();

        for (int i = 0; i < MAX_VIRTUAL; i++) {
            int j = i;
            Thread thread = builder.unstarted(
                    ()    -> {
                        Task.ioIntesive(j);
                        latch.countDown();
                    }
            );
            thread.start();
        }
        latch.await();
    }
}
