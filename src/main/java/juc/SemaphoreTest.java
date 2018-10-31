package juc;

import java.util.concurrent.Semaphore;

/**
 * 信号量测试
 * @author LiuMengKe
 * @create 2018-10-31-9:35
 * 八个工人五台机器
 */
public class SemaphoreTest {

    private final static int MACHINE_NUM = 5;
    private final static int WORKER_NUM = 12;
    private Semaphore semaphore = new Semaphore(MACHINE_NUM);

    public static void main(String[] args) {
        SemaphoreTest test = new SemaphoreTest();
        for (int i = 0; i < WORKER_NUM; i++) {
            Worker worker = test.new Worker(test.semaphore);
            worker.start();
        }
    }

    class Worker extends Thread{
        private Semaphore semaphore;
        public Worker(Semaphore semaphore) {
            this.semaphore =semaphore;
        }
        @Override
        public void run() {
            try {
                //阻塞方式获取锁
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+"获得锁");
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName()+"执行完毕释放锁");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
