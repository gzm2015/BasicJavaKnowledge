package juc;

import org.junit.Test;

/**
 * 主线程子线程关系测试
 * 主线程也只是一个普通线程 子线程开启后和主线程无关继续自己执行完毕
 */
public class MainSonThreadTest {

    /**
     * 正常执行
     * mainmain thread stop
     * myThread2   my thread run
     * myThread   my thread run
     * myThread   my thread stop
     * myThread2   my thread stop
     */
    /*public static void main(String[] args) {
        Thread thread = new SonThread("myThread");
        thread.start();
        Thread thread2 = new SonThread("myThread2");
        thread2.start();
        System.out.println(Thread.currentThread().getName()+"main thread stop");
    }*/


    /**
     * 正常运行
     */
    /*public static void main(String[] args) {

        Thread thread1 = new Thread(new MyRunnable());
        thread1.setName("Thread1===");
        thread1.start();

        Thread thread2 = new Thread(new MyRunnable());
        thread2.setName("Thread2===");
        thread2.start();

        System.out.println(Thread.currentThread().getName()+"main thread stop");
    }*/

    /**
     * junit但测试不支持多线程
     */
    @Test
    public void threadTest() throws InterruptedException {
        Thread thread = new SonThread("myThread");
        thread.start();
        thread.join();

        Thread thread1 = new Thread(new MyRunnable());
        thread1.setName("runnableThread");
        thread1.start();
        System.out.println(Thread.currentThread().getName()+"main thread stop");

    }

    /**
     * 子线程正常
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new SonThread("myThread");
        thread.start();
        thread.join();
        Thread thread1 = new Thread(new MyRunnable());
        thread1.setName("runnableThread");
        thread1.start();
        System.out.println(Thread.currentThread().getName()+"main thread stop");
    }
}

class SonThread extends Thread{
    public SonThread(String name) {
        super(name);
    }
    @Override
    public void run() {
        try {
            super.run();
            System.out.println(Thread.currentThread().getName()+"   my thread run");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"   my thread stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"runnalbe test");
    }
}