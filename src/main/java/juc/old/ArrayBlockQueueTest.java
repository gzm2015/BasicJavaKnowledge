package juc.old;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author gzm2015
 * @create 2018-10-30-20:50
 * 阻塞队列
 */
public class ArrayBlockQueueTest {

    /**
     *     常见的阻塞队列方法
     *     put方法用来向队尾存入元素，如果队列满，则等待；
     * 　　take方法用来从队首取元素，如果队列为空，则等待；
     * 　　offer方法用来向队尾存入元素，如果队列满，则等待一定的时间，当时间期限达到时，如果还没有插入成功，则返回false；否则返回true；
     * 　　poll方法用来从队首取元素，如果队列空，则等待一定的时间，当时间期限达到时，如果取到，则返回null；否则返回取得的元素；
     */
    private ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(10);

    public static void main(String[] args) {

        ArrayBlockQueueTest test = new ArrayBlockQueueTest();
        Producter producter = test.new Producter();
        Customer customer = test.new Customer();
        producter.start();
        customer.start();

    }

    class Customer extends Thread {
        @Override
        public void run() {
            System.out.println("customer 等待阻塞队列对象");
            //Object object = blockingQueue.poll();//如果使用poll 不会阻塞等待
            Object object = null;
            try {
                object = blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("从阻塞队列中取出对象" + object);
        }
    }

    class Producter extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println("阻塞队列中放入一个对象");
                blockingQueue.put("put into queue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
