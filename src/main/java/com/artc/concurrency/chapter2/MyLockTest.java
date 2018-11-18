package com.artc.concurrency.chapter2;

/**
 * 100 次测试
 * 每次 1000 个线程 买 100 张票
 */
public class MyLockTest {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            testSale();
        }
    }

    private static void testSale() throws InterruptedException {
        final TicketSale ticketSale = new TicketSale(100);
        int opCount = 1000;
        // 保证子线程执行完毕
        for (int i = 0; i < opCount; i++) {
            new Thread(() -> ticketSale.sale(1)).start();
        }
        Thread.sleep(1000); // 睡1秒, 保证子线程执行完毕
        System.out.println("the tickets still " + ticketSale.tickets + " .");
        if (ticketSale.tickets != 0)
            throw new IllegalArgumentException("tickets not enough ...");
    }

    private static class TicketSale {

        private int tickets;

        private MyLock lock = new MyLock();

        public TicketSale(int tickets) {
            this.tickets = tickets;
        }

        public void sale(int i) {
            lock.lock();

            if (tickets <= 0)
                return;

            tickets -= i;

            lock.unlock();
        }

    }

}
