package juc.latest;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:lmk
 * @Date: 2020/4/7   17:31
 * @Description: 两个线程依次打印1 A 2B 3C
 */
public class PrintTest {

    public static Thread threadInPark;
    public static Thread threadInPark2;


    public static void main(String[] args) {
        //传统方式 wait notify 打印
        //traditionalPrint();
        //LockSupport方式
        //park();
        //ReentranLock
        //reentranLock();
        transferQueue();//
    }

    //阻塞队列方式
    public static void transferQueue(){
        char[] chars1 = new char[]{'1', '2', '3'};
        char[] chars2 = new char[]{'A', 'B', 'C'};

        TransferQueue transferQueue = new LinkedTransferQueue();
        Thread thread = new Thread(() -> {
            for (char c : chars1) {
                try {
                    transferQueue.transfer(c);
                    //打印线程2塞进来的
                    System.out.print(transferQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        Thread thread2 = new Thread(() -> {
            for (char c : chars2) {
                try {
                    System.out.print(transferQueue.take());
                    transferQueue.transfer(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread2.start();
    }

    public static void reentranLock() {
        char[] chars1 = new char[]{'1', '2', '3'};
        char[] chars2 = new char[]{'A', 'B', 'C'};
        ReentrantLock reentrantLock = new ReentrantLock();
        //通过condition唤醒同一个类型的线程
        Condition condition1 = reentrantLock.newCondition();
        Condition condition2 = reentrantLock.newCondition();
        //注意此处 lock unlock 与循环的位置关系
        Thread thread = new Thread(() -> {
            reentrantLock.lock();
            try {
                for (char c : chars1) {
                    System.out.print(c);
                    condition2.signal();
                    condition1.await();
                }
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

        });
        Thread thread2 = new Thread(() -> {
            reentrantLock.lock();
            try {
                for (char c : chars2) {
                    System.out.print(c);
                    condition1.signal();
                    condition2.await();
                }
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

        });
        thread.start();
        thread2.start();
    }

    public static void park() {

        char[] chars1 = new char[]{'1', '2', '3'};
        char[] chars2 = new char[]{'A', 'B', 'C'};


        threadInPark = new Thread(() -> {
            for (char c : chars1) {
                System.out.print(c);
                LockSupport.unpark(threadInPark2);
                LockSupport.park();
            }
        });

        Thread finalThread1 = threadInPark;
        threadInPark2 = new Thread(() -> {
            for (char c : chars2) {
                LockSupport.park();
                System.out.print(c);
                LockSupport.unpark(finalThread1);
            }
        });

        threadInPark.start();
        threadInPark2.start();
    }


    public static void traditionalPrint() {
        Object ob = new Object();
        char[] chars1 = new char[]{'1', '2', '3'};
        char[] chars2 = new char[]{'A', 'B', 'C'};

        Thread thread = new Thread(() -> {
            synchronized (ob) {
                for (char c : chars1) {
                    System.out.print(c);
                    ob.notify();
                    try {
                        ob.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ob.notify();
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (ob) {
                for (char c : chars2) {
                    try {
                        ob.notify();
                        ob.wait();
                        System.out.print(c);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ob.notify();
            }
        });

        thread.start();
        thread2.start();
    }

}
