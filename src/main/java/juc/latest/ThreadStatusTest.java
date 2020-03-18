package juc.latest;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author LiuMengKe
 * @create 2019-01-10-14:40
 */
public class ThreadStatusTest extends BasicThreadTest{

    /**
     * 1.主动等待
     *  wait() 释放锁 回到runnable状态
     *  join()内部调用的是wait() 释放锁  当前线程进入watting状态 等待isAlive()跳出while(isAlive())
     *
     * 2.主动睡眠
     *  sleep(seconds)  不释放锁 回到runnable状态
     *  wait(seconds)
     *  join(seconds)
     *
     *  interrupt 线程被阻塞时(IO阻塞 同步块阻塞) 由其他线程调用interrupt方法可以抛出异常中断阻塞状态
     *  yield 不释放锁 回去和其他线程公平竞争
     */


    /**
     * 执行结果 线程2 在线程1得到锁开始睡眠以后 依然拿不到锁 睡眠结束以后调用wait方法立刻就拿到了锁
         2 线程开始执行
         1 线程开始执行
         2 得到锁     进入睡眠 系统时间1547104823096
         2 睡眠结束  调用wait方法并立刻释放锁  系统时间1547104828100
         1 得到锁     进入睡眠 系统时间1547104828100
         1 睡眠结束  调用wait方法并立刻释放锁  系统时间1547104833107
         2 wait 方法结束
         1 wait 方法结束
     */
    @Test
    public void waitTest() throws Throwable{
        Object lock = new Object();
        AbstractThread[] waitThreads = (AbstractThread[])Arrays.asList(
                new WaitThread("1",lock),new WaitThread("2",lock)
        ).toArray();
        runTest(waitThreads);
    }


    /**
     * yield 仅仅只是让步而已 重新回到runnalbe状态 让其他线程执行 并不会释放锁 其他线程没有锁还是会由原线程执行
     * 1 线程开始执行
     * 2 线程开始执行
     * 1 得到锁     进入睡眠 系统时间1547105490804
     * 1 睡眠结束  调用yield方法并让步与其他线程回到runnalbe状态  系统时间1547105495811
     * 1 yield方法结束  继续睡眠后调用 yield方法1547105495811
     * 1 离开线程
     * 2 得到锁     进入睡眠 系统时间1547105500824
     * 2 睡眠结束  调用yield方法并让步与其他线程回到runnalbe状态  系统时间1547105505836
     * 2 yield方法结束  继续睡眠后调用 yield方法1547105505836
     * 2 离开线程
     */
    @Test
    public void yieldTest() throws Throwable{

        Object lock = new Object();
        AbstractThread[] waitThreads = (AbstractThread[])Arrays.asList(
                new YieldThread("1",lock),new YieldThread("2",lock)
        ).toArray();
        runTest(waitThreads);

    }


    /**
     * join 测试join底层调用的是wait方法所以也会释放锁
     */
    @Test
    public void joinTest() throws Throwable{

        Thread t1 = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                System.out.println("t1 is runnning");
                try {
                    Thread.sleep(i*500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    System.out.println("t2 begin running");
                    Thread.sleep(3000);
                    t1.join(); //等待t1结束以后继续执行
                    System.out.println("t2 is running");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        t1.start();
        t2.start();

        Thread.sleep(8000);
        System.out.println("main over");

    }




    /**
     * yield()不会释放锁标志。
     */
    public class YieldThread extends AbstractThread{

        public YieldThread(String name, Object lock) {
            super(name, lock);
        }

        @Override
        public void crorun() throws Throwable {
            for (int i = 0; i < 30; i++) {
                synchronized (super.getLock()){
                    System.out.println(super.getName()+":"+i);
                    System.out.println(super.getName()+" yield");
                    Thread.yield();
                }
            }
        }
    }




    public class WaitThread extends AbstractThread{

        public WaitThread(String name, Object lock) {
            super(name,lock);
        }

        @Override
        public void crorun() throws Throwable{
            System.out.println(this.getName()+" 线程开始执行");
            synchronized (super.getLock()){
                System.out.println(this.getName()+" 得到锁     进入睡眠 系统时间"+System.currentTimeMillis());
                Thread.sleep(5000);
                System.out.println(this.getName()+" 睡眠结束  调用wait方法并立刻释放锁  系统时间"+System.currentTimeMillis());
                super.getLock().notifyAll();
                super.getLock().wait();
                System.out.println(this.getName()+" wait 方法结束");
                super.getLock().notifyAll();
            }
        }
    }




}
