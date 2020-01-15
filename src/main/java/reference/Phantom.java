package reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @Author:lmk
 * @Date: 2020/1/8   16:57
 * @Description: 对于幻象引用，有时候也翻译成虚引用 ,你不能通过它访问对象。幻象引用仅仅是提供了一种确保对象被 fnalize 以后，做某些事情的机制
 *  * 3. {@link PhantomReference} 虚引用，在GC时发现虚引用对象，会将{@link PhantomReference}插入{@link ReferenceQueue}。 此时对象未被真正回收，要等到{@link ReferenceQueue}被真正处理后才会被回收。
 */
public  class Phantom extends ReferenctTest{

    public static void main(String[] args) {
        test();
    }

    /**
     * 虚引用 的 get 总是 null
     */
    public static void test() {
        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference ref = new PhantomReference(new User("kk", 11), queue);
        System.out.println(ref.get());
    }


}
