package juc.latest;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author gzm2015
 * @create 2018-10-27-20:52
 * description ： synchronized 关键字测试
 * synchronized 可以使用类锁(用在静态方法上和静态代码块）和对象锁用在(静态代码块和方法上)
 */
public class SynChronizedThreadTest extends BasicThreadTest{

    //普通方法synchronized 修饰加对象锁
    @Test
    public void objectLockOnMethodTest() throws Throwable{
        SynChronizedObjectLock synChronizedObjectLock = new SynChronizedObjectLock();
        SynChronizedObjThread t1 = new SynChronizedObjThread("t1", synChronizedObjectLock);
        SynChronizedObjThread t2 = new SynChronizedObjThread("t2", synChronizedObjectLock);
        AbstractThread[] waitThreads = (AbstractThread[])Arrays.asList(
                t1,t2
        ).toArray();
        runTest(waitThreads);
    }

    //普通代码块synchronized 修饰加对象锁
    @Test
    public void objectLockOnBlockTest() throws Throwable{
        SynChronizedObjectLock synChronizedObjectLock = new SynChronizedObjectLock();
        SynChronizedObjThread2 t1 = new SynChronizedObjThread2("t1", synChronizedObjectLock);
        SynChronizedObjThread2 t2 = new SynChronizedObjThread2("t2", synChronizedObjectLock);
        AbstractThread[] waitThreads = (AbstractThread[])Arrays.asList(
                t1,t2
        ).toArray();
        runTest(waitThreads);
    }

    //修饰静态方法加类锁
    @Test
    public void classLockOnStaticMethodTest() throws Throwable{
        SynChronizedClassLock synChronizedClassLock = new SynChronizedClassLock();
        SynChronizedClassLock synChronizedClassLock2 = new SynChronizedClassLock();
        SynChronizedClzThread t1 = new SynChronizedClzThread("t1", synChronizedClassLock);
        SynChronizedClzThread t2 = new SynChronizedClzThread("t2", synChronizedClassLock2);
        AbstractThread[] waitThreads = (AbstractThread[])Arrays.asList(
                t1,t2
        ).toArray();
        runTest(waitThreads);
    }


    class SynChronizedObjectLock {
        public synchronized void print(){
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName()+"   running     "+i+"       times");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class SynChronizedObjThread extends AbstractThread{
        public SynChronizedObjThread(String name, SynChronizedObjectLock lock) {
            super(name,lock);
        }
        @Override
        public void crorun() {
            SynChronizedObjectLock lock =  (SynChronizedObjectLock)super.getLock();
            lock.print();
        }
    }


    class SynChronizedObjThread2 extends AbstractThread{
        public SynChronizedObjThread2(String name, SynChronizedObjectLock lock) {
            super(name,lock);
        }
        @Override
        public void crorun() {
            synchronized (super.getLock()){
                SynChronizedObjectLock lock =  (SynChronizedObjectLock)super.getLock();
                lock.print();
            }
        }
    }


    //synchronized 修饰静态方法使用类锁


    static class SynChronizedClzThread extends AbstractThread{

        public SynChronizedClzThread(String name, SynChronizedClassLock lock) {
            super(name,lock);
        }
        @Override
        public void crorun() {
            SynChronizedClassLock.print();
        }
    }

    static class SynChronizedClassLock {
        public static synchronized void print(){
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println(Thread.currentThread().getName()+"   running     "+i+"       times");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}







