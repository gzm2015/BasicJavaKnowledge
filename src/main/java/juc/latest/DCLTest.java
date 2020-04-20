package juc.latest;

/**
 * @Author:lmk
 * @Date: 2020/4/20   11:31
 * @Description: 测试双重检查锁问题
 */
public class DCLTest {

    public static void main(String[] args) {

        Runnable runnable = ()->{
            Demo.getInstance();
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread3.start();
    }

}

class Demo{

    private static Demo demo;

    public static Demo getInstance(){
        if(demo==null){
            synchronized (Demo.class){
                if(demo==null){
                    System.out.println("new 了");
                    return new Demo();
                }
            }
        }
        return demo;
    }
}
