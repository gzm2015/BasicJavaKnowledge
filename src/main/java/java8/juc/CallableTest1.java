package java8.juc;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author:lmk
 * @Date: 2020/3/18   17:07
 * @Description: 想要 明白 CompletableFuture 需要先懂 callable 和 future
 */
public class CallableTest1 {

    /**
     * callable练习
     */
    public static void main(String[] args) {
        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(10);
            return "abc";
        };

        Callable<String> callable1 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "abc";
            }
        };

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        Future<String> future = cachedThreadPool.submit(callable);

        int count = 0;

        while (!future.isDone()){
            System.out.println("结果未取到 sleep 1s 后继续");
            try {
                TimeUnit.SECONDS.sleep(1);
                count++;
                //取5次后还没准备好则取消
                if(count>5){
                    future.cancel(true);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            //取消后get CancellationException
            if(!future.isCancelled()){
                String resp = future.get();
                System.out.println(resp);
            }else {
                System.out.println("cancel");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        Callable<String> callable3 = () -> {
            TimeUnit.SECONDS.sleep(1);
            return "callable3";
        };
        Callable<String> callable4 = () -> {
            TimeUnit.SECONDS.sleep(3);
            return "callable4";
        };
        Callable<String> callable5 = () -> {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("callable5 sleep over");
            return "callable5";
        };


        try {
            //invokeAll 获取的 future list 的第一个future 会阻塞到所有 callable zhunb 完成
            //顺序打印callable3 4 5
            List<Future<String>> futures =  cachedThreadPool.invokeAll(Arrays.asList(callable3, callable4, callable5));
            for (Future<String> stringFuture : futures) {
               System.out.println(stringFuture.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        /**
         * 随意一个完成就行 得倒callable3
         */
        try {
            //invokeAll 获取的 future list 的第一个future 会阻塞到所有 callable zhunb 完成
            String any =  cachedThreadPool.invokeAny(Arrays.asList(callable3, callable4, callable5));
            System.out.println(any);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }


}
