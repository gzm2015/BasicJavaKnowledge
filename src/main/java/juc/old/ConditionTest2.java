package juc.old;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gzm2015
 * @create 2018-10-31-21:48
 *
 * await signal
 */
public class ConditionTest2 {

    int size = 2;
    private PriorityQueue queue = new PriorityQueue(size);
    private Lock lock = new ReentrantLock();
    private Condition customerCondition = lock.newCondition();
    private Condition producterCondition = lock.newCondition();

    public static void main(String[] args) {
        ConditionTest2 test2 = new ConditionTest2();
        ConditionTest2.Producter t1 = test2.new Producter();
        ConditionTest2.Customer t2 = test2.new Customer();
        t1.start();
        t2.start();
    }

    class Customer extends Thread {
        @Override
        public void run() {
            //不断尝试
            for (;;) {
                lock.lock();
                try {
                    //如果queue 队列没得东西就阻塞
                    while (queue.size() == 0) {
                        try {
                            customerCondition.await();
                        } catch (InterruptedException e) {
                            queue.notify();
                            e.printStackTrace();
                        }
                    }
                    Object ob = queue.poll();
                    System.out.println("customer get    " + ob);
                    producterCondition.signal();
                }finally {
                    lock.unlock();
                }
            }
        }

        void customer() {

        }
    }

    class Producter extends Thread {
        @Override
        public void run() {
            for (;;) {
                lock.lock();
                try {
                    while (queue.size() == size) {
                        try {
                            producterCondition.await();
                        } catch (InterruptedException e) {
                            queue.notify();
                            e.printStackTrace();
                        }

                    }
                    queue.offer(1);
                    System.out.println("customer offer");
                    customerCondition.signal();
                }finally {
                    lock.unlock();
                }
            }
        }

        void producter() {

        }
    }
}
