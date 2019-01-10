package juc.latest;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

/**
 * CroboUtils线程基类
 *
 * @author LiuMengKe
 * @create 2019-01-10-14:45
 */
@Data
@AllArgsConstructor
public abstract class AbstractThread  extends TestRunnable {

    private String name;
    private Object lock;

    public abstract void crorun() throws Throwable;

    @Override
    public void runTest() throws Throwable {
        crorun();
    }

}
