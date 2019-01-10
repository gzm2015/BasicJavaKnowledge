package juc.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author gzm2015
 * @create 2018-10-30-9:14
 */
public class RentReadWriteLockTest {

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        RentReadWriteLockTest test = new RentReadWriteLockTest();
        Thread t1 = new Thread(()->test.readlock());
        t1.start();
        Thread t2 = new Thread(()->test.readlock());
        t2.start();
    }


    private void readlock() {
        rwl.readLock().lock();
        System.out.println(Thread.currentThread().getName()+"获取读锁");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"   i");
        }
        rwl.readLock().unlock();
    }

}
