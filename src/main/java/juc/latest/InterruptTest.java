package juc.latest;

/**
 * @author LiuMengKe
 * @create 2019-01-10-17:15
 */
public class InterruptTest {


    /**
     * 阻塞的线程调用interrupt以后会抛出一异常
     * 阻塞的方法io synchronized块
     */
    public static void main(String[] args) {
        Object lock = new Object();

        Thread t1 = new Thread(() -> {

            for (int i = 0; i < 20; i++) {
                try {
                    synchronized (lock) {
                        System.out.println("t1 running ");
                        Thread.sleep(2500);
                        if (Thread.currentThread().isInterrupted()) {
                            System.out.println("t1非阻塞线程检测到中断标志位变化 中断线程");
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        Thread t2 = new Thread(() -> {

            for (int i = 0; i < 20; i++) {
                try {
                    synchronized (lock) {
                        System.out.println("t2 running ");
                        Thread.sleep(2500);
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println("主线程 interrupte t2成功 t2 抛出异常InterruptedException t2开始中断t1");
                    t1.interrupt();
                    break;
                }
            }

        });


        //t2没有锁被阻塞 要10s以后拿到锁
        t2.start();
        try {
            Thread.sleep(2500);
            t1.start();
            Thread.sleep(1500);
            t2.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }


}
