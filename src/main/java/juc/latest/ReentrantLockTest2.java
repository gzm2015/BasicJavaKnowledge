package juc.latest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:lmk
 * @Date: 2020/4/26   09:41
 * @Description:
 */
public class ReentrantLockTest2 {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread t1 = new Thread(()->{
            lock.lock();
            System.out.println(Thread.currentThread().getName()+":get lock");
            try {
                condition.await();
                System.out.println(Thread.currentThread().getName()+":await over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(()->{
            lock.lock();
            System.out.println(Thread.currentThread().getName()+":get lock");
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName()+":signal");
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        t2.start();



    }
}
