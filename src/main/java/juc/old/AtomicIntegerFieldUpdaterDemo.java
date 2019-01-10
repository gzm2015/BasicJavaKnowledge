package juc.old;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdater
 * 对属性字段进行原子操作
 * 必须使用volatile修饰
 * 由于是通过反射获取属性并进行值的操作只能满足修饰符
 * 父类的属性字段无法获取 private的修饰的也无法获取
 */
public class AtomicIntegerFieldUpdaterDemo {
   class FatherData{
       protected volatile int value5 = 5;
       public volatile int value6 = 5;
   }
   class DemoData extends FatherData{
       public volatile int value1 = 1;
       volatile int value2 = 2;
       protected volatile int value3 = 3;
       private volatile int value4 = 4;
   }
    AtomicIntegerFieldUpdater<DemoData> getUpdater(String fieldName) {
        return AtomicIntegerFieldUpdater.newUpdater(DemoData.class, fieldName);
    }
    void doit() {
        DemoData data = new DemoData();
        System.out.println("1 ==> "+getUpdater("value1").getAndSet(data, 10));
        System.out.println("2 ==> "+getUpdater("value2").incrementAndGet(data));
        System.out.println("3 ==> "+getUpdater("value3").decrementAndGet(data));
        System.out.println("true ==> "+getUpdater("value6").compareAndSet(data, 5, 5));
        System.out.println("true ==> "+getUpdater("value5").compareAndSet(data, 5, 5));
        System.out.println("true ==> "+getUpdater("value4").compareAndSet(data, 4, 5));
    }
    public static void main(String[] args) {
        AtomicIntegerFieldUpdaterDemo demo = new AtomicIntegerFieldUpdaterDemo();
        demo.doit();
    }
} 