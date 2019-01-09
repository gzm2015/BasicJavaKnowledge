package juc;

import java.util.concurrent.*;

/**
 * @author gzm2015
 * @create 2018-10-31-20:22
 * Future Callable 配合使用 Future 是一个接口
 * 一种是直接继承Thread，另外一种就是实现Runnable接口。
 * 这2种方式都有一个缺陷就是：在执行完任务之后无法获取执行结果。
 * Callable 和 ExecutorService 一起使用 返回Future
 */
public class CallableFutureTest {

    public static void main(String[] args) {
        CallableFutureTest test = new CallableFutureTest();
        //Callable 可以有返回结果
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(test.new Task());
        System.out.println("主线程在执行任务");
        try {
            System.out.println(future.get());
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
        }
    }

    class Task implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            return 1;
        }
    }

}
