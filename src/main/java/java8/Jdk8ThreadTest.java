package java8;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author LiuMengKe
 * @create 2018-05-25-14:33
 */
public class Jdk8ThreadTest {

    @Test
    public void test() {

        Runnable thread1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("6666666666");
            }
        };

        new Thread(thread1).start();


        new Thread(()->{
            System.out.printf("6666666");
        }).start();
    }


    @Test
    public void testExecute() {
        Runnable thread1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("ffffffff");
                System.out.println("6666666666");
            }
        };
        Runnable thread2 = ()-> System.out.println("999999999999");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(thread1);
        ExecutorService executorService1 = Executors.newCachedThreadPool();
        Future future = executorService1.submit(thread2);
        System.out.println(future.isDone());

    }


    @Test
    public void testCallable() {
        try {
            Callable callable = ()->"aaaaa";
            FutureTask futureTask = new FutureTask(callable);
            Thread thread = new Thread(futureTask);
            thread.start();
            System.out.println(futureTask.get());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testCallable2() {
        try {
            Callable callable = ()->{
                TimeUnit.MINUTES.sleep(1);
                return  "aaaaa";
            };
            FutureTask futureTask = new FutureTask(callable);
            Thread thread = new Thread(futureTask);
            thread.start();
            System.out.println(futureTask.get(1,TimeUnit.SECONDS));
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }

    }

    @Test
    public void testaa() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(1);

            Future<Integer> future = executor.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    return 123;
                }
                catch (InterruptedException e) {
                    throw new IllegalStateException("task interrupted", e);
                }
            });

            while (!future.isDone()){

            }
            System.out.println(future.get(1, TimeUnit.SECONDS));
        }catch (Exception e){
            System.out.println("error");
        }

    }


    @Test
    public void testSchedule() {
        ScheduledExecutorService executor =         Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            }
            catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };

        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);

    }
}
