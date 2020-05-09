package juc.latest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:lmk
 * @Date: 2020/5/9   13:43
 * @Description:
 */
public class ConditionTest {

    public static void main(String[] args) {

        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        Condition condition2 = reentrantLock.newCondition();

        Thread t1 = new Thread(()->{
            while (true) {
                try {
                    reentrantLock.lock();
                    System.out.println("t1 is running");
                    TimeUnit.SECONDS.sleep(1);
                    condition2.signal();
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }

        });

        Thread t2 = new Thread(()->{
            while (true) {
                try {
                    reentrantLock.lock();
                    System.out.println("t2 is running");
                    TimeUnit.SECONDS.sleep(1);
                    condition.signal();
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }

        });

        t1.start();
        t2.start();

    }



}
