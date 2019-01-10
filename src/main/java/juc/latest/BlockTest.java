package juc.latest;

/**
 * @author LiuMengKe
 * @create 2019-01-10-20:08
 * 阻塞测试
 * 阻塞测试 一个线程调用locK.wait()以后如果没有其他线程调用 lock.notify 或者notifyAll 则会一直处于阻塞状态
 *         另外就是synchronized一直在获取锁的状态也会一直处于阻塞状态
 */
public class BlockTest extends BasicThreadTest{

    /**
     * 线程阻塞测试 使用wait处于阻塞状态以后 其他线程一调用interrupt方法以后就会捕获到异常
     * 而使用synchronized块一直阻塞 要等对应的锁被其他线程释放以后才能捕获
     */
    /**
     * 调用wait后处于阻塞状态
     */
    public static void main(String[] args) {

        Object lock = new Object();
        Thread t1 = getThread("t1",()->{
            synchronized (lock){
                for (int i = 0; i < 10; i++) {
                    try {
                        System.out.println("t1 waiting ");
                        lock.wait();
                        System.out.println("t1 wait end");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("t1 捕获中断异常");
                        return;
                    }
                }
            }
        });

        t1.start();


        Thread t2 = getThread("t2",()->{
            synchronized (lock){
                for (int i = 0; i < 10; i++) {
                    try {
                        System.out.println("t2 is running");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("t2 捕获中断异常");
                        return;
                    }
                }
            }
        });

        Thread t3 = getThread("t3",()->{
            System.out.println("t3 running");
            synchronized (lock){
                for (int i = 0; i < 10; i++) {
                    try {
                        System.out.println("t3 is running");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("t3 捕获中断异常");
                        return;
                    }
                }
            }
        });


        try {
            Thread.sleep(1000);
            //确保t2开启的时候t1已经释放了锁
            t2.start();
            System.out.println("主线程中断t1线程");
            t1.interrupt();
            Thread.sleep(1000);
            t3.start();
            //t3 一直拿不到锁让其处于阻塞状态
            Thread.sleep(1000);
            t3.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("主线程结束");
    }



}
