package juc.latest;

/**
 * @author LiuMengKe
 * @create 2019-01-10-17:15
 */
public class InterruptTest {


    /**
     * 阻塞的线程调用interrupt以后会抛出一异常
     * 抛出异常后isinterrupt状态会被重置
     *
     */
    public static void main(String[] args) {
        Object lock = new Object();

        Thread t1 = new Thread(() -> {
            System.out.println("t1 begin running");
            try {
                synchronized (lock) {
                    System.out.println("t1 get lock");
                    Thread.sleep(15000);
                    System.out.println("t1 free lock");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("阻塞态t1 中断 开始运行");
            }
        });


        try {
            t1.start();
            System.out.println("中断前 interrupted 状态位"+t1.isInterrupted());
            Thread.sleep(100);
            t1.interrupt();
            System.out.println("中断后 抛出异常后 interrupted 状态位"+t1.isInterrupted());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
