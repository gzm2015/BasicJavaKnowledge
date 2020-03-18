package juc.old;

/**
 * @author gzm2015
 * @create 2018-10-27-20:52
 * description ： synchronized 关键字测试
 * synchronized 可以使用类锁(用在静态方法上和静态代码块）和对象锁用在(静态代码块和方法上)
 */
public class SynChronizedThreadTest {
    //测试synchronized修饰方法使用对象锁
    //t1 t2使用同一把锁
    /*public static void main(String[] args) {
        MethodSyncObj synChronizedObjectLock = new MethodSyncObj();
        MethodThread t1 = new MethodThread("t1", synChronizedObjectLock);
        t1.start();
        MethodThread t2 = new MethodThread("t2", synChronizedObjectLock);
        t2.start();
    }*/

    /**
     * 测试sync代码块使用同一个锁
     */
    /*public static void main(String[] args) {
        BlockSyncObj blockSyncObj = new BlockSyncObj();
        BlockThread t1 = new BlockThread("t1", blockSyncObj);
        t1.start();
        BlockThread t2 = new BlockThread("t2", blockSyncObj);
        t2.start();
    }*/

    //测试synchronized修饰方法使用对象锁
    //t1 t2 两个线程 用两个对象 但是使用的是同一把锁
    /*public static void main(String[] args) {
        StaticMethodSyncObj staticMethodSyncObj = new StaticMethodSyncObj();
        StaticMethodSyncObj staticMethodSyncObj2 = new StaticMethodSyncObj();
        StaticMethodThread t1 = new StaticMethodThread("t1", staticMethodSyncObj);
        t1.start();
        StaticMethodThread t2 = new StaticMethodThread("t2", staticMethodSyncObj2);
        t2.start();
    }*/


    public static void main(String[] args) {
        ClassSyncObj staticMethodSyncObj = new ClassSyncObj();
        ClassSyncObj staticMethodSyncObj2 = new ClassSyncObj();
        ClassThread t1 = new ClassThread("t1", staticMethodSyncObj);
        t1.start();
        ClassThread t2 = new ClassThread("t2", staticMethodSyncObj2);
        t2.start();
    }
}

//synchronized 修饰方法使用对象锁
class MethodSyncObj {

    public synchronized void print(){
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName()+"   running     "+i+"       times");
        }
    }
}
class MethodThread extends Thread{
    private MethodSyncObj lock;
    public MethodThread(String name, MethodSyncObj lock) {
        super(name);
        this.lock = lock;
    }
    @Override
    public void run() {
        lock.print();
    }
}


//使用代码块上加锁
class BlockSyncObj {

    public  void print(){
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName()+"   running     "+i+"       times");
        }
    }
}
class BlockThread extends Thread{
    private BlockSyncObj lock;
    public BlockThread(String name, BlockSyncObj lock) {
        super(name);
        this.lock = lock;
    }
    @Override
    public void run() {
        synchronized (lock){
            lock.print();
        }
    }
}



//synchronized 修饰静态方法使用类锁
class StaticMethodSyncObj {
    public static synchronized void print(){
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName()+"   running     "+i+"       times");
        }
    }
}

class StaticMethodThread extends Thread{

    private StaticMethodSyncObj lock;
    public StaticMethodThread(String name, StaticMethodSyncObj lock) {
        super(name);
        this.lock = lock;
    }
    @Override
    public void run() {
        lock.print();
    }
}

class ClassSyncObj {
    public  void print(){
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName()+"   running     "+i+"       times");
        }
    }
}

class ClassThread extends Thread{

    private ClassSyncObj lock;

    public ClassThread(String name, ClassSyncObj lock) {
        super(name);
        this.lock = lock;
    }
    @Override
    public void run() {
        synchronized (ClassThread.class){
            lock.print();
        }
    }
}