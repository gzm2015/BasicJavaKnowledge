package juc.old;

import java.util.concurrent.CountDownLatch;

/**
 * @author gzm2015
 * @create 2018-10-31-9:12
 * CountDownLatch 当一个计数器用
 * https://www.cnblogs.com/dolphin0520/category/602384.html
 */
public class CountDownLatchTest {

    private CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) {
        CountDownLatchTest test = new CountDownLatchTest();

        new Thread(()->{
            System.out.println("线程1开始执行");
            try {
                Thread.sleep(2000);
                test.latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程1执行完毕");
        }).start();


        new Thread(()->{
            try {
                System.out.println("线程2开始执行");
                Thread.sleep(6000);
                test.latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2执行完毕");
        }).start();


        try {
            System.out.println("等待两个线程执行完毕");
            test.latch.await();
            System.out.println("计数器归零");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
