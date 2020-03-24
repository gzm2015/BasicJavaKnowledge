package java8.juc;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
*
* 体会惰性求值的作用
* */
public class LogDemo {

  private static final Logger logger = Logger
      .getLogger(LogDemo.class.getName());

  @Override
  public String toString() {
    System.out.println("这个方法执行了, 耗时1秒钟");
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
    }

    return "LogDemo";
  }

  public void test() {
    // 如果不加判断直接打印, 会有额外多余的开销, 就算最终日志并没有打印
    //logger.fine("打印一些日志:" + this);
    logger.fine(() -> "打印一些日志:" + this);
  }


  /**
   * 如果test不用lambda 也不设置日志级别则 日志不会被打印 但是toString方法会被调用 因为和 字符串拼接直接作为参数的时候马上就调用了
   * 使用lambda设置级别以后可以打印
   * jdk8的stream流编程里面，没有调用最终操作的时候，中间操作的方法都不会执行，这也是惰性求值
   * 所以这里传递lambda不会执行toString
   * lambda 持有的this 和 匿名内部类不同 匿名内部类指向的是自身而 lambda 相当是在类中产生对应class增加 lambda$(number) 方法
   * 如果没有使用this则此时产生的lambda$(number)是静态的
   * 如果使用了lambda$(number)是非静态的
   */
  public static void main(String[] args) {
    LogDemo demo = new LogDemo();
    logger.setLevel(Level.FINE);
    System.out.println(logger.getLevel());
    demo.test();
  }
}