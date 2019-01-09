package juc;

/**
 * 中断线程测试
 * @author gzm2015
 * @create 2018-10-27-19:58
 * description: 阻塞的线程 被其他线程调用了 interrupt以后会抛出InterruptedException异常
 */
public class InterRuptThreadTest {

    //测试阻塞的线程被调用interrupt
    /*public static void main(String[] args) {
        Thread t1 = new BlockingInterRuptThread("blockThread");
        t1.start();
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }*/

    //测试非阻塞线程被调用interrupt能够捕获到标志位的变化不会抛出InterruptedException异常
    public static void main(String[] args) {
        Thread t1 = new NotBlockingInterRuptThread("notblockThread");
        t1.start();
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }

}


class BlockingInterRuptThread extends Thread{
    public BlockingInterRuptThread(String name) {
        super(name);
    }
    @Override
    public void run() {
        for (;;){
            try {
                super.run();
                System.out.println(Thread.currentThread().getName()+" thread running");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+"中断异常");
                e.printStackTrace();
                break;
            }
        }
    }
}


class NotBlockingInterRuptThread extends Thread{
    public NotBlockingInterRuptThread(String name) {
        super(name);
    }
    @Override
    public void run() {
        for (;;){
            try {
                super.run();
                System.out.println(Thread.currentThread().getName()+" thread running");
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("非阻塞线程检测到中断标志位变化");
                    break;
                }
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName()+"中断异常");
                e.printStackTrace();
                break;
            }
        }
    }
}