package com.artc.concurrency.chapter1;

/**
 * 多线程性能测试
 * 1. 一个比较尴尬的问题: 我的电脑是多核的, 多线程都比单线程快
 * 2. 书中测试的性能为 操作不超过 100W 的情况下 , 但线程更快
 *
 * 0. 多线程上下文切换消耗性能可以通过, 工具: Lmbench3 分析
 */
public class ThreadPerformanceDemo {

    private final static int COUNTER = 1;

    public static void main(String[] args) throws Exception {
        ThreadPerformanceDemo demo = new ThreadPerformanceDemo();
        demo.singleThread();
        demo.multiThread();
    }

    private void multiThread() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> this.part1(COUNTER));
        Thread thread2 = new Thread(() -> this.part2(COUNTER));
        thread.start();
        thread2.start();
        thread.join();
        thread2.join();
        System.out.println("multi thread execute time >>>> " + (System.currentTimeMillis() - start));
    }

    private void singleThread() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            part1(COUNTER);
            part2(COUNTER);
        });
        thread.start();
        thread.join();
        System.out.println("single thread execute time >>>> " + (System.currentTimeMillis() - start));
    }

    private void part1(int optionCounter) {
        long sum = 0;
        for (int i = 0; i < optionCounter; i++) {
            sum = sum + i;
        }
        System.out.println("ThreadPerformanceDemo.part1 sum = " + sum);
    }

    private void part2(int optionCounter) {
        long sum = 0;
        for (int i = 0; i < optionCounter; i += 5) {
            sum = sum + i;
        }
        System.out.println("ThreadPerformanceDemo.part2 sum = " + sum);
    }
}
