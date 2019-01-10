package juc.latest;

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
        //t1 t2 可重入
        Thread t3 = new Thread(()->test.writeLock());
        t3.start();
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

    private void writeLock() {
        rwl.writeLock().lock();
        System.out.println(Thread.currentThread().getName()+"获取写锁");
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
