package juc;

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
        SynChronizedObjectLock synChronizedObjectLock = new SynChronizedObjectLock();
        SynChronizedObjThread t1 = new SynChronizedObjThread("t1", synChronizedObjectLock);
        t1.start();
        SynChronizedObjThread t2 = new SynChronizedObjThread("t2", synChronizedObjectLock);
        t2.start();
    }*/

    public static void main(String[] args) {
        SynChronizedObjectLock2 synChronizedObjectLock2 = new SynChronizedObjectLock2();
        SynChronizedObjThread2 t1 = new SynChronizedObjThread2("t1", synChronizedObjectLock2);
        t1.start();
        SynChronizedObjThread2 t2 = new SynChronizedObjThread2("t2", synChronizedObjectLock2);
        t2.start();
    }

    //测试synchronized修饰方法使用对象锁
    //t1 t2 两个线程 用两个对象 但是使用的是同一把锁
    /*public static void main(String[] args) {
        SynChronizedClassLock synChronizedClassLock = new SynChronizedClassLock();
        SynChronizedClassLock synChronizedClassLock2 = new SynChronizedClassLock();
        SynChronizedClzThread t1 = new SynChronizedClzThread("t1", synChronizedClassLock);
        t1.start();
        SynChronizedClzThread t2 = new SynChronizedClzThread("t2", synChronizedClassLock2);
        t2.start();
    }*/
}

//synchronized 修饰方法使用对象锁
class SynChronizedObjectLock {

    public synchronized void print(){
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName()+"   running     "+i+"       times");
        }
    }
}
class SynChronizedObjThread extends Thread{
    private SynChronizedObjectLock lock;
    public SynChronizedObjThread(String name, SynChronizedObjectLock lock) {
        super(name);
        this.lock = lock;
    }
    @Override
    public void run() {
        lock.print();
    }
}


//使用代码块上加锁
class SynChronizedObjectLock2 {

    public  void print(){
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName()+"   running     "+i+"       times");
        }
    }
}
class SynChronizedObjThread2 extends Thread{
    private SynChronizedObjectLock2 lock;
    public SynChronizedObjThread2(String name, SynChronizedObjectLock2 lock) {
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
class SynChronizedClassLock {
    public static synchronized void print(){
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName()+"   running     "+i+"       times");
        }
    }
}

class SynChronizedClzThread extends Thread{

    private  SynChronizedClassLock lock;
    public SynChronizedClzThread(String name, SynChronizedClassLock lock) {
        super(name);
        this.lock = lock;
    }
    @Override
    public void run() {
        lock.print();
    }
}