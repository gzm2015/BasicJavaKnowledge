package juc.latest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:lmk
 * @Date: 2020/4/20   16:08
 * @Description:
 */
public class Test {

    private static int count = 0;

    /**
     * 可见性问题导致覆盖问题
     */
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(20);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    count++;
                }
                countDownLatch.countDown();
            }
        };
        for (int i = 1; i <= 20; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }
}

/**
 * 悲观锁控制
 */
class Test2 {

    private static  int count = 0;

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    synchronized (Test2.class){
                        count++;
                    }
                }
                countDownLatch.countDown();
            }
        };
        for (int i = 1; i <= 10; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result: " + count);
    }
}

/**
 * cas乐观锁自增
 */
class CasTest{
    static AtomicInteger integer = new AtomicInteger();

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    integer.incrementAndGet();
                }
                countDownLatch.countDown();
            }
        };
        for (int i = 1; i <= 10; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result: " + integer.get());
    }
}