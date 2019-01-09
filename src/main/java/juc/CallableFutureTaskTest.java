package juc;

import java.util.concurrent.*;

/**
 * @author gzm2015
 * @create 2018-10-31-20:36
 * <p>
 * FutureTask implements RunnableFuture<V> extends Runnable, Future<V>
 */
public class CallableFutureTaskTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        CallableFutureTaskTest taskTest = new CallableFutureTaskTest();
        try {
            FutureTask task = new FutureTask(taskTest.new Task());
            executor.submit(task);//submit(Runnable able);
            System.out.println(task.get());
        } catch (InterruptedException |ExecutionException e) {
            e.printStackTrace();
        }

    }

    class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            return 1;
        }
    }

}
