package com.artc.concurrency.chapter2;

/**
 * volatile 实现状态锁
 */
public class MyLock {

    private volatile int state;

    public boolean lock() {

        if (state == 0) { // volatile 保证读取的是最新值
            state = 1; // volatile 写, 与 synchronize 有相同的语义.
            // JVM 会在此处插入 StoreLoad 屏障, 保证写入操作对后续的读取操作可见 (可见性) .
            return true;
        }

        return false;
    }

    public boolean unlock() {

        if (state == 1) {
            state = 0;
            return true;
        }

        return false;
    }

}
