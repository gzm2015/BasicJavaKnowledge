package reference;

import java.lang.ref.SoftReference;

/**
 * @Author:lmk
 * @Date: 2020/1/8   16:57
 * @Description: 软引用在要发生oom的时候会被gc回收 注意写法 直接把对象放入SoftReference构造函数内
 */
public  class Soft extends ReferenctTest{

    public static void main(String[] args) {
        test2();
        //test3();
    }


    /**
     * 弱引用 在内存足够的时候 即时发生了gc 也不会被回收 并且 里面的对象直接指向对象地址 即使引用丢失也没关系
     */
    public static void test2() {
        SoftReference<User> softReference = new SoftReference<>(new User("kk", 11));
        System.gc();
        System.out.println("soft hashcode"+softReference.get().hashCode());
        System.out.println(softReference.get());
    }

    /**
     * 弱引用 会在发生outof memeory 前发生回收
     */
    public static void test3() {
        SoftReference<User> softReference = new SoftReference<>(new User("kk", 11));
        wasteMemory();
        System.out.println(softReference.get());
    }

}
