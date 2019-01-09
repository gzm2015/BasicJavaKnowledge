package juc;

import java.util.PriorityQueue;

/**
 * @author gzm2015
 * @create 2018-10-30-21:24
 * 阻塞队列联系
 * wait()、notify/notifyAll() 方法是Object的本地final方法，无法被重写。
 * wait 调用以后释放持有对象锁 running 状态变为 runnalbe状态
 * notify方法只唤醒一个等待（对象的）线程并使该线程开始执行notify唤醒沉睡的线程后，线程会接着上次的执行继续往下执行
 * wait()、notify/notifyAll() 在synchronized 代码块执行，说明当前线程一定是获取了锁的。
 */
public class BlockQueueTest2 {

    private static final int CAP = 10;
    private PriorityQueue queue = new PriorityQueue(CAP);


    /**
     * add(E e):将元素e插入到队列末尾，如果插入成功，则返回true；如果插入失败（即队列已满），则会抛出异常；
     * <p>
     * 　　remove()：移除队首元素，若移除成功，则返回true；如果移除失败（队列为空），则会抛出异常；
     * <p>
     * 　　offer(E e)：将元素e插入到队列末尾，如果插入成功，则返回true；如果插入失败（即队列已满），则返回false；
     * <p>
     * 　　poll()：移除并获取队首元素，若成功，则返回队首元素；否则返回null；
     * <p>
     * 　　peek()：获取队首元素，若成功，则返回队首元素；否则返回null
     */
    public static void main(String[] args) {
        BlockQueueTest2 test2 = new BlockQueueTest2();
        Producter t1 = test2.new Producter();
        Customer t2 = test2.new Customer();
        t1.start();
        t2.start();
    }

    class Producter extends Thread {
        @Override
        public void run() {
            product();
        }

        private void product() {
            //同时使用队列对象进行加锁 保证每次只有一个线程在get或者put
            for (; ; ) {
                synchronized (queue) {
                    //如果达到最大容量了就wait
                    while (queue.size() == CAP) {
                        try {
                            System.out.println("队列满，等待有空余空间");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    //小于最大容量就是
                    queue.offer("add object");
                    queue.notify();
                    System.out.println("向队列取中插入一个元素，队列剩余空间：" + (CAP - queue.size()));

                }
            }
        }

    }

    class Customer extends Thread {
        @Override
        public void run() {
            customer();
        }

        private void customer() {
            for (; ; ) {
                synchronized (queue) {
                    while (queue.size() == 0) {
                        try {
                            System.out.println("队列空，等待数据");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    //小于最大容量就是
                    queue.poll();
                    queue.notify();
                    System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");

                }
            }

        }
    }
}
