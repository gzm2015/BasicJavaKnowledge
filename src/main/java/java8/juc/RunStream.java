package java8.juc;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 验证stream运行机制
 * 
 * 1. 所有操作是链式调用, 一个元素只迭代一次 
 * 2. 每一个中间操作返回一个新的流. 流里面有一个属性sourceStage 
 *     指向同一个 地方,就是Head 
 * 3. Head->nextStage->nextStage->... -> null
 * 4. 有状态操作会把无状态操作阶段,单独处理
 * 5. 并行环境下, 有状态的中间操作不一定能并行操作.
 * 
 * 6. parallel/ sequetial 这2个操作也是中间操作(也是返回stream)
 *     但是他们不创建流, 他们只修改 Head的并行标志
 * 
 * @author 晓风轻
 *
 * https://xwjie.github.io/webflux/webflux-study-path.html
 * stream 可以分为中断操作和中间操作
 *        其中不能放stream的都属于中断操作 findAndy findfirst max min reduce foreach count.......
 *
 *        短路操作和非短路操作
 *        Stream.generate 这种产生无限流的操作需要一个终止产生流的操作
 *        limit
 *
 *        中间操作分为有状态无状态
 *        无状态是只和自己有关  比如 map filter
 *        其他是有状态操作 比如 distinct sorted
 *
 *        操作的时候，我们需要把无状态操作写在一起，有状态操作放到最后，这样效率会更加高
 *
 */
public class RunStream {

  public static void main(String[] args) {
    Random random = new Random();
    // 随机产生数据
    Stream<Integer> stream = Stream.generate(() -> random.nextInt())
        // 产生500个 ( 无限流需要短路操作. )
        .limit(10)
        // 第1个无状态操作
        .peek(s -> print("peek: " + s))
        // 第2个无状态操作
        .filter(s -> {
          print("filter: " + s);
          return s > 1000000;
        })
        // 有状态操作
        .sorted((i1, i2) -> {
          print("排序: " + i1 + ", " + i2);
          return i1.compareTo(i2);
        })
        // 又一个无状态操作
        .peek(s -> {
          print("peek2: " + s);
        }).parallel();

    // 终止操作
      long count = stream.count();
      System.out.println(count);
  }

  /**
   * 打印日志并sleep 5 毫秒
   * 
   * @param s
   */
  public static void print(String s) {
    // System.out.println(s);
    // 带线程名(测试并行情况)
    System.out.println(Thread.currentThread().getName() + " > " + s);
    try {
      TimeUnit.MILLISECONDS.sleep(5);
    } catch (InterruptedException e) {
    }
  }
}