package reference;

import java.lang.ref.WeakReference;

/**
 * @Author:lmk
 * @Date: 2020/1/8   16:57
 * @Description: weak 引用只要发生了gc就会被回收
 */
public  class Weak extends ReferenctTest{

    public static void main(String[] args) {
        //test();
        //test2();
        test3();
    }

    /**
     * 弱引用只要发生了gc 必然被回收 可能导致无法获取到里面的对象
     */
    public static void test() {
        WeakReference<User> weakReference = new WeakReference<>(new User("kk", 11));
        System.gc();
        System.out.println(weakReference.get());
    }

    /**
     * 弱引用 get
     */
    public static void test2() {
        WeakReference<User> weakReference = new WeakReference<>(new User("kk", 11));
        System.out.println(weakReference.get());
    }

    /**
     * 弱引用
     * 内存不够自动gc
     */
    public static void test3() {
        WeakReference<User> weakReference = new WeakReference<>(new User("kk", 11));
        System.out.println(weakReference.get());
        wasteMemory();
    }


}
