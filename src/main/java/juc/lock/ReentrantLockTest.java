package juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gzm2015
 * @create 2018-10-27-22:20
 *
 * ReentrantLock 实现lock接口
 *     void lock();
 *     void lockInterruptibly() throws InterruptedException;  锁起来以后可以被中断后抛出异常
 *     boolean tryLock();  尝试获取锁 马上返回true false
 *     boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
 *     void unlock();
 *     Condition newCondition();
 */
public class ReentrantLockTest {

    //基本加锁解锁
    /*public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        RentrantBasicThread t1 = new RentrantBasicThread("t1",lock);
        t1.start();
        RentrantBasicThread t2 = new RentrantBasicThread("t2",lock);
        t2.start();
    }*/

/*    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        RentrantTryLockThread t1 = new RentrantTryLockThread("t1",lock);
        t1.start();
        RentrantTryLockThread t2 = new RentrantTryLockThread("t2",lock);
        t2.start();
    }*/


    //lock.lockInterruptibly(); 由interrupt触发
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        RentrantInterruptThread t1 = new RentrantInterruptThread("t1",lock);
        t1.start();
        t1.interrupt();
        RentrantInterruptThread t2 = new RentrantInterruptThread("t2",lock);
        t2.start();
    }
}

class RentrantBasicThread extends Thread{

    private ReentrantLock lock;

    public RentrantBasicThread(String name, ReentrantLock lock) {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"获得锁");
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+i);
            }
        }finally {
            //在finally释放锁
            System.out.println(Thread.currentThread().getName()+"释放锁");
            lock.unlock();
        }
    }
}


class RentrantTryLockThread extends Thread{

    private ReentrantLock lock;

    public RentrantTryLockThread(String name, ReentrantLock lock) {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {

        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName()+"获得锁");
                for (int i = 0; i < 10; i++) {
                    Thread.currentThread().sleep(1000);
                    System.out.println(Thread.currentThread().getName()+i);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            finally {
                //在finally释放锁
                System.out.println(Thread.currentThread().getName()+"释放锁");
                lock.unlock();
            }
        }else {
            System.out.println(Thread.currentThread().getName()+"获取锁失败");
            System.out.println(Thread.currentThread().getName()+"获取锁失败后打断");
        }

    }
}


class RentrantInterruptThread extends Thread{

    private ReentrantLock lock;

    public RentrantInterruptThread(String name, ReentrantLock lock) {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {

        try {
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName()+"获得锁");
            for (int i = 0; i < 10; i++) {
                lock.lockInterruptibly();
                Thread.currentThread().sleep(10000);
                System.out.println(Thread.currentThread().getName()+i);
            }
        }catch (InterruptedException  e){
            System.out.println(Thread.currentThread().getName()+"InterruptedException interrupt被捕获");
            e.printStackTrace();
        }
        catch (Exception e){
            System.out.println(Thread.currentThread().getName()+"Exception interrupt被捕获");
            e.printStackTrace();
        }
        finally {
            //在finally释放锁
            System.out.println(Thread.currentThread().getName()+"释放锁");
            lock.unlock();
        }
    }
}