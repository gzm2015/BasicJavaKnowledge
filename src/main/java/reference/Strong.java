package reference;

/**
 * @Author:lmk
 * @Date: 2020/1/8   16:57
 * @Description: 普通对象都是强引用 只要还有引用 即使发生了 gc 宁可抛出oom 也不会在一个方法的执行中 被gc
 */
public  class Strong extends ReferenctTest{

    public static void main(String[] args) {
        //test();
        //test2();
        //test3();
        test4();
    }

    /**
     * 强引用只要还有引用存在就不会因为发生了gc而被垃圾回收
     */
    public static void test() {
        ReferenctTest.User user = new ReferenctTest.User("kk", 11);
        System.gc();
    }

    /**
     * 强引用引用 置空 发生gc后对象被回收
     */
    public static void test2() {
        ReferenctTest.User user = new ReferenctTest.User("kk", 11);
        user = null;
        System.gc();
    }

    /**
     * 强引用只要还有引用存在 即时发生了 oom 也不会回收对象
     */
    public static void test3() {
        ReferenctTest.User user = new ReferenctTest.User("kk", 11);
        wasteMemory();
    }


    /**
     * 强引用引用 要溢出了发生了gc 回收了无引用对象内存
     */
    public static void test4() {
        ReferenctTest.User user = new ReferenctTest.User("kk", 11);
        user = null;
        wasteMemory();
    }



}
