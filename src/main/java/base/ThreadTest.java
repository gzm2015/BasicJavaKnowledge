package base;

import org.junit.Test;

public class ThreadTest {

    @Test
    public void threadTest(){

        Thread thread = new MyThread("myThread");
        thread.start();

        Thread thread1 = new Thread(new MyRunnable());
        thread1.start();

        System.out.println(Thread.currentThread().getName()+"main thread stop");

    }


    public class MyThread extends Thread{

        public MyThread(String name) {
            super(name);
        }
        @Override
        public void run() {
            try {
                super.run();
                System.out.println(Thread.currentThread().getName()+"   my thread run");
                Thread.sleep(10000);
                System.out.println(Thread.currentThread().getName()+"   my thread stop");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("runnalbe test");
        }
    }

}
