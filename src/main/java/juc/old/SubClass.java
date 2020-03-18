package juc.old;

public class SubClass extends SuperClass implements Runnable {
    @Override
    public void run(){
        this.subSynFun();
    }
    //子类的同步方法
    public synchronized  void subSynFun(){
        System.out.println("子类的同步方法");
        this.superSynFun();
    }

    public static void main(String[] args) {
        SubClass sub=new SubClass();
        Thread t =new Thread(sub);
        t.start();
    }

}
//父类
class SuperClass{
    //父类的同步方法
    public synchronized void superSynFun(){
        System.out.println("父类的同步方法");
    }
}