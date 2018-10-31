package juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author LiuMengKe
 * @create 2018-10-31-9:20
 * CyclicBarrier回环测试 主要用法是 等待所有线程执行完毕以后 一起执行一下剩下的任务
 */
public class CyclicBarrierTest {

    private static final int BARRIER_NUM = 4;

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(BARRIER_NUM);
        for (int i = 0; i < BARRIER_NUM; i++) {
            Thread t = new  CyclicBarrierThread(barrier);
            t.start();
        }
    }

    static class CyclicBarrierThread extends Thread {
        private CyclicBarrier barrier;

        public CyclicBarrierThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try {
                Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(BrokenBarrierException e){
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }

}