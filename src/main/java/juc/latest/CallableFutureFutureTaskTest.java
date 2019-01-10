package juc.latest;

import org.junit.Test;

import java.util.concurrent.*;

public class CallableFutureFutureTaskTest {

    @Test
    public void callable() {
        //Callable 可以有返回结果
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(()->{
            System.out.println("子线程在进行计算");
            return 1;
        });
        System.out.println("主线程在执行任务");
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void futureTask() {
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            FutureTask<Integer> task = new FutureTask<>(()->{
                System.out.println("子线程在进行计算");
                return 1;
            });
            executor.submit(task);//submit(Runnable able);
            System.out.println(task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
