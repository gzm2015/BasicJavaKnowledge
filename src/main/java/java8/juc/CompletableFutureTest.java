package java8.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


        //thenCompose 可以将两个有依赖关系的future 结合起来
        //因为有依赖关系得到的是compose2的结果
        CompletableFuture<String> thenCompose = getcompose1("compose1").thenCompose(CompletableFutureTest::getcompose2);

        //thenCombine 适合没有依赖关系的两个future结合
        //得到的结果是 bitfuntion中返回的结果
        CompletableFuture<String> combinedFuture = getcompose1("combine1",8).thenCombine(getcompose1("combine2",1),(s, s2) ->{
            System.out.println("combine param1: "+s);
            System.out.println("combine param2: "+s2);
            return "hello";
        } );

        try {
            System.out.println("thenCompose resp :"+thenCompose.get());
            System.out.println("thenCombine resp " + combinedFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        //Returns a new CompletableFuture that is completed when all of the given CompletableFutures complete.
        //阻塞直到所有的都返回
        CompletableFuture<String> allof1 = getcompose1("allof1");
        CompletableFuture<String> allof2 = getcompose1("allof2", 5);
        CompletableFuture<Void> allOfFuture =  CompletableFuture.allOf(allof1,allof2);
        CompletableFuture<Void> allendFuture = allOfFuture.thenApply(aVoid -> Stream.of(allof1,allof2).map(CompletableFuture::join).collect(Collectors.joining("   "))).thenAccept(System.out::println);

        CompletableFuture<String> anyof1 = getcompose1("anyof1");
        CompletableFuture<String> anyof2 = getcompose1("anyof2", 5);
        CompletableFuture<Object> anyOfFuture =  CompletableFuture.anyOf(anyof1,anyof2);
        try {
            System.out.println("allOfFuture resp:"+allendFuture.get());
            System.out.println("anyofFuture resp:"+anyOfFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }



        //异常处理
        //不论有无异常痘会进入handle进行处理
        CompletableFuture<String> exfuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("exfuture run over   ");
            return "exfuture";
        }).thenApply(s -> {
            if(true){
                throw new IllegalArgumentException();
            }
            return "ok";
        }).handle((o, throwable) -> {
            if (throwable!=null){
                System.out.println("Oops! We have an exception - " + throwable.getMessage());
                return "Unknown!";
            }
            return o;
        });

    }

    public static CompletableFuture<String> getcompose1(String composeName){
        return getcompose1(composeName, 1);
    }


    public static CompletableFuture<String> getcompose1(String composeName, long timeout){
        CompletableFuture<String> compose1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(composeName+" run over");
            return composeName;
        });
        return compose1;
    }

    public static CompletableFuture<String> getcompose2(String username){
        CompletableFuture<String> compose2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("compose2 run over   "+username);
            return "compose2";
        });
        return compose2;
    }
}
