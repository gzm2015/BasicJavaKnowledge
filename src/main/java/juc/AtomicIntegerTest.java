package juc;

import org.junit.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.Assert.*;


public class AtomicIntegerTest {

    @Test
    public void testAll() throws InterruptedException{
        final AtomicInteger value = new AtomicInteger(10);
        //compareAndSet(int expect, int update) 如果当前值 == 预期值，则以原子方式将该值设置为给定的更新值。
        assertEquals(value.compareAndSet(1, 2), false);
        assertEquals(value.get(), 10);
        assertTrue(value.compareAndSet(10, 3));
        assertEquals(value.get(), 3);
        value.set(0);
        //incrementAndGet 以原子方式将当前值加 1。返回更新的值
        assertEquals(value.incrementAndGet(), 1);
        //getAndAdd 以原子方式将给定值与当前值相加。  返回：以前的值
        assertEquals(value.getAndAdd(2),1);
        //以原子方式设置为给定值，并返回旧值。
        assertEquals(value.getAndSet(5),3);
        assertEquals(value.get(),5);
        //
        final int threadSize = 10;
        Thread[] ts = new Thread[threadSize];
        for (int i = 0; i < threadSize; i++) {
            ts[i] = new Thread() {
                public void run() {
                    value.incrementAndGet();
                }
            };
        }
        //
        for(Thread t:ts) {
            t.start();
        }
        for(Thread t:ts) {
            t.join();
        }
        //
        assertEquals(value.get(), 5+threadSize);
    }

}