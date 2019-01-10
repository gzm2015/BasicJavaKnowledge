package juc.old;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gzm2015
 * @create 2018-10-31-21:24
 *     Conditon中的await()对应Object的wait()；
 * 　　 Condition中的signal()对应Object的notify()；
 * 　　 Condition中的signalAll()对应Object的notifyAll()。
 */
public class ConditionTest {
    int size = 2;
    private PriorityQueue queue = new PriorityQueue(size);
    private Lock lock = new ReentrantLock();
    private Condition customerCondition = lock.newCondition();
    private Condition producterCondition = lock.newCondition();

    public static void main(String[] args) {
        ConditionTest test2 = new ConditionTest();
        Producter t1 = test2.new Producter();
        Customer t2 = test2.new Customer();
        t1.start();
        t2.start();
    }


    class Customer extends Thread{
        @Override
        public void run() {
            //不断尝试
            for(;;){
                synchronized (queue){
                    //如果queue 队列没得东西就阻塞
                    while (queue.size()==0){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            queue.notify();
                            e.printStackTrace();
                        }
                    }

                    Object ob = queue.poll();
                    System.out.println("customer get    "+ob);
                    queue.notify();;
                }
            }
        }

        void customer(){

        }
    }

    class Producter extends Thread{
        @Override
        public void run() {
            for(;;){
                synchronized (queue){
                    while (queue.size()==size){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            queue.notify();
                            e.printStackTrace();
                        }

                    }
                    queue.offer(1);
                    System.out.println("customer offer");
                    queue.notify();
                }
            }
        }

        void producter(){

        }
    }

}
