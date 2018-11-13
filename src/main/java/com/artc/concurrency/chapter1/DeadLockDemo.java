package com.artc.concurrency.chapter1;

/**
 * 死锁: 多个线程相互等待锁的释放的情况
 * 避免死锁:
 * 1. 避免一个线程获取多个锁
 * 2. 避免锁嵌套 (PS)
 * 3. 使用定时锁, timeout 自动释放锁
 * 4. 操作数据库的时候, 加锁解锁操作确定是在同一链接的情况, 否则可能解锁失败
 *
 * 死锁的出现可能在很多复杂的场景中:
 * a. socket 网络通信的时候, 两端等待读
 * b. 耗时操作中, timeout
 * c. '流' 的操作中
 * ... 各种阻塞的场景均有可能
 */
public class DeadLockDemo {

    /**
     * 本例执行结果:
     *
     * Thread-0 : 等待 lockA
     * Thread-0 : 获取到了 lockA
     * Thread-1 : 等待 lockB
     * Thread-1 : 获取到了 lockB
     * Thread-0 : 等待 lockB
     * Thread-1 : 等待 lockA
     * ...
     */
    public static void main(String[] args) {

        String lockA = "A";
        String lockB = "B";

        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " : 等待 lockA");
            synchronized (lockA) {
                System.out.println(threadName + " : 获取到了 lockA");
                try {
                    // 模拟逻辑处理
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // ignore
                }
                System.out.println(threadName + " : 等待 lockB");
                synchronized (lockB) {
                    System.out.println(threadName + " : 获取到了 lockB");
                }
            }
        }).start();

        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " : 等待 lockB");
            synchronized (lockB) {
                System.out.println(threadName + " : 获取到了 lockB");
                try {
                    // 模拟逻辑处理
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // ignore
                }
                System.out.println(threadName + " : 等待 lockA");
                synchronized (lockA) {
                    System.out.println(threadName + " : 获取到了 lockA");
                }
            }
        }).start();
    }

}
