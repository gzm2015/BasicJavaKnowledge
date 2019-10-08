package java8;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author gzm2015
 * @create 2018-05-25-14:33
 * <p>
 * 不应该使用junit 来测试线程 测试方法结束以后测试方法中新开的线程还没运行完也看不到结果了
 * 如果一定要这么用可以使用join 等待完成
 *
 * 1.使用Runnable 新用法 ()->
 * 2.Future future = executorService.submit(Callable callable)
 * 3.FutureTask 相当于 Runnable, Future 构造方法是 new FutureTask(Callable<V> callable)
 *   所以既有Runnable的特性放入Thread构造函数又能够
 * 4.TimeUnit.SECONDS.sleep(1); 睡眠固定时间
 * 5.Executors.newScheduledThreadPool 定时执行线程池
 *
 */
public class ThreadTest {

    @Test
    public void start() {
        Runnable thread1 = new Runnable() {
            @Override
            public void run() {
               /* try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                System.out.println("传统方式启动线程");
            }
        };
        Thread clasicThread = new Thread(thread1);
        clasicThread.start();

        /*try {
            clasicThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        new Thread(() -> {
            System.out.printf("新方式启动线程");
        }).start();
    }


    @Test
    public void testExecute() {
        Runnable classicType = new Runnable() {
            @Override
            public void run() {
                System.out.println("classic type");
            }
        };
        Runnable lambdaType = () -> System.out.println("lambda type");
        Callable<String> callable = () -> "callable";

        ExecutorService runnableService = Executors.newFixedThreadPool(2);
        runnableService.submit(classicType);
        runnableService.submit(lambdaType);

        ExecutorService callableService = Executors.newCachedThreadPool();
        Future future = callableService.submit(callable);
        try {
            Assert.assertEquals("callable", future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testCallable() {
        try {
            Callable callable = () -> "aaaaa";
            FutureTask futureTask = new FutureTask(callable);
            Thread thread = new Thread(futureTask);
            thread.start();
            System.out.println(futureTask.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Callable 内睡眠十分钟 futureTask 1s后get 只能得到占位对象
     *          内睡眠1s   futureTask 10s后get 得到了正常的对象
     */
    @Test
    public void threadFutureTask() {
        try {
            Callable callable = () -> {
                //TimeUnit.MINUTES.sleep(1);
                TimeUnit.SECONDS.sleep(1);
                return "aaaaa";
            };
            FutureTask futureTask = new FutureTask(callable);
            Thread thread = new Thread(futureTask);
            thread.start();
            //System.out.println(futureTask.get(1, TimeUnit.SECONDS));
            System.out.println(futureTask.get(10, TimeUnit.SECONDS));
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }

    /**
     * future is done 用法
     */
    @Test
    public void executorServiceCallableLambda() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(1);

            Future<Integer> future = executor.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    return 123;
                } catch (InterruptedException e) {
                    throw new IllegalStateException("task interrupted", e);
                }
            });

            while (!future.isDone()) {

            }
            System.out.println(future.get(1, TimeUnit.SECONDS));
        } catch (Exception e) {
            System.out.println("error");
        }

    }


    @Test
    public void testSchedule() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            } catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };
        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            } catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };
        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
    }
}
