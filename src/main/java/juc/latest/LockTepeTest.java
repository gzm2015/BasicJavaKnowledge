package juc.latest;

import lombok.Data;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

public class LockTepeTest {


    /**
     * 锁的种类
     * <p>
     * 可重入锁
     * 公平锁非公平锁 ReentrantLock 默认是非公平锁 构造方法里面可以设置
     * 读锁写锁 ReentrantReadWriteLock
     * 中断锁 ReentrantLock lock.lockInterruptibly();
     */

    //可重入锁测试
    @Test
    public void reenTrantLockTest() {
        NormalLockObj ob = new NormalLockObj();
        Thread t1 = new Thread(() -> ob.method());
        t1.start();

        ReentrantLockObj ob2 = new ReentrantLockObj();
        ob2.setLock(new ReentrantLock());
        Thread t2 = new Thread(() -> ob2.method());
        t2.start();
    }

    class NormalLockObj {
        public synchronized void method() {
            System.out.println("method1");
            method2();
        }

        public synchronized void method2() {
            System.out.println("method2");
        }
    }

    @Data
    class ReentrantLockObj {

        private ReentrantLock lock;

        public void method() {
            lock.lock();
            System.out.println("method1");
            method2();
            lock.unlock();
        }

        public void method2() {
            lock.lock();
            System.out.println("method2");
        }
    }


}
