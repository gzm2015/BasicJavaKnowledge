package base;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTest {

    /**
     * random 是根据seed生成的伪随机数
     * 另外如果 使用无参构造方法的时候使用是自旋锁保证的
     *
     * SecureRandom 使用它替代Random
     */
    @Test
    public void random(){

        int seed = 10;
        Random random1 = new Random(seed);
        Random random2 = new Random(seed);

        System.out.println("random1------------------");
        System.out.println(random1.nextInt(10));
        System.out.println(random1.nextInt(10));
        System.out.println("random2------------------");
        System.out.println(random2.nextInt(10));
        System.out.println(random2.nextInt(10));

    }
    
    /**
     * 不同线程之间使用ThreadLocalRandom比较合适
     */
    @Test
    public void threadrandom(){

        Thread t1 = new MyThread("t1");
        Thread t2 = new MyThread("t2");
        t1.start();
        t2.start();
    }

    public class MyThread extends Thread{
        public MyThread(String name) {
            super(name);
        }
        @Override
        public void run() {
            ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            System.out.println(threadLocalRandom.nextInt(10));
        }
    }

}
