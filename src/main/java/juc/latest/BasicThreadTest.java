package juc.latest;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

/**
 * @author LiuMengKe
 * @create 2019-01-10-14:10
 */
public class BasicThreadTest {


    public void runTest(TestRunnable[] trs) throws Throwable{
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        // 开发并发执行数组里定义的内容
        mttr.runTestRunnables();
    }

    public static Thread getThread(String threadName, Runnable runnable){
        Thread thread = new Thread(runnable);
        thread.setName(threadName);
        return thread;
    }
}
