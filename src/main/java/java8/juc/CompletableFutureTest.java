package java8.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author:lmk
 * @Date: 2020/3/18   17:35
 * @Description:
 */
public class CompletableFutureTest {

    public static void main(String[] args) {
        //runAsync 执行不需要结果的
        CompletableFuture future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("runAsync run over");
        });

        //runAsync 执行需要结果的
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync run over");
            return "supplyAsync";
        });

        //thenApply 可以连续向下传递改变 future get 的回调结果
        CompletableFuture<String> future3 = future2.thenApply(s -> s+"   then apply").thenApply(s -> s+" second apply");

        //不要结果 thenAccept thenRun
        CompletableFuture<Void> future4 = future2.thenAccept(s -> System.out.println(s+"    future4 runing"));

        try {
            System.out.println("prepare get");
            System.out.println("future get:"+future.get());
            System.out.println("future2 get:"+future2.get());
            System.out.println("future3 get:"+future3.get());
            System.out.println("future4 get:"+future4.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }



        CompletableFuture<String> compose1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("compose1 run over");
            return "compose1";
        });


        CompletableFuture<String> kk = compose1.thenCompose(s -> getcompose2(s));
        try {
            System.out.println(kk.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }


    public static CompletableFuture<String> getcompose2(String username){
        CompletableFuture<String> compose2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("compose2 run over   "+username);
            return "compose2";
        });
        return compose2;
    }
}
