package generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import static javafx.scene.input.KeyCode.T;

/**
 * 泛型测试
 *
 * @author gzm2015
 * @create 2019-01-07-10:55
 */
public class GenericTest {


    /**
     * 泛型就相当于一个类型参数 编译前控制入参 增加可读性 减少强转
     */

    /**
     * 泛型类中映入类型变量T,U
     * 常见类型变量及其指代  E 集合 K,V 键值 T、U、S 任意类型
     */
    @Test
    public void GenericTest() {
        PairSon<String, Integer> pair = new PairSon<>("first", 2);
        System.out.println(pair);
        pair.print("fff");
    }

    /**
     * 泛型的类型擦除
     * 不论类型变量定义在类上还是定义在方法上
     * 编译后都是一个对象类型 如果没有特别的类型限制则编译后背替换为 Object
     * 如果限制类型了，则被替换为限制后的类型 T extends Comparable  则类型参数全部被替换为Comparable
     * 这样会在多态和类型擦除上产生矛盾
     */
    @Test
    public void EraseTest() {
        PairSon<String, Integer> pair = new PairSon<>("first", 2);
        pair.print("son");
    }

    /**
     * 泛型的限制
     * 1.类型参数不能使用基本数据类型
     * 2.泛型查询的时候只能比较原始类型 不能对类型参数进行比较 类似的泛型原始类的getClass
     * 3.不能用泛型类创建泛型数组
     * 4.不能实例化类型参数 如在Pair类中使用 new T()
     * 5.同4 不能构造泛型数组 new T[5];
     * 6.泛型类中静态 域或方法中 使用类型变量
     * 7.既不能抛出也不能捕获泛型类型变量 除非类型变量 声明的时候extends Throwables
     * 8.注意参数擦除后的冲突问题 类或者类型变量不能同时成为两个接口类型的子类 而这两个接口实际上是同一个接口
     */
    @Test
    public void RestrictionTest() {
        //1.类型参数不能使用基本数据类型
        //PairSon<String,int> pair;

        //2.泛型查询的时候只能比较原始类型 不能对类型参数进行比较 类似的泛型原始类的getClass
        /*Pair<String,Integer> pair = new Pair<>("first",2);
        Pair<String,String> pair2 = new Pair<>("first","second");
        Assert.assertTrue(pair instanceof Pair);
        Assert.assertEquals(pair.getClass(),pair2.getClass());*/

        //3.不能用泛型类创建泛型数组
        //Pair<String,Integer>[] pairArray = new  Pair<String,Integer>[10];

        //4.不能实例化类型参数 如在Pair类中使用 new T()

        //5.泛型类中静态 域或方法中 使用类型变量
        //Pair 类 静态变量 public static T t;  public static  <T> invoke(T t);

        //6.注意参数擦除后的冲突问题 类或者类型变量不能同时成为两个接口类型的子类 而这两个接口实际上是同一个接口
        /**
         * 要想支持擦除的转换 ， 就需要强行限制一个类或类
         * 型变量不能同时成为两个接口类型的子类
         * ， 而这两个接口是同一接口的不同参数化
         * Manager 会实现 Comparable < Employee > 和 Comparable < Manager > , 这是同一接口 的不同
         * 参数化
         * 这一限制与类型擦除的关系并不十分明确
         * 。 毕竟 ， 下列非泛型版本是合法的 。
         * class Employee implements Comparable { . . . }
         * class Manager extends Employee implements Comparable { . . . }
         * 其原因非常微妙
         * ， 有可能与合成的桥方法产生冲突 。 实现了 C 0 mpamble < X > 的类可以获得一
         * 个桥方法 ：
         */
       /* class Employee implements Comparable<Employee>;
        class Manager extends Employee implements Comparable<Manager>;*/
    }


    @Test
    public void complexTest() {
        Manager ceo = new Manager("jack", 100, "10");
        Manager cto = new Manager("ma", 1000, "50");

        Pair<Manager> buddies = new Pair<>(ceo, cto);
        printBuddies(buddies);

        Manager[] managers = {ceo,cto};
        Pair<Employee> result = new Pair<>();
        minmaxLevel(managers,result);
        printBuddies(result);
        maxminLevel(managers,result);
        printBuddies(result);
    }

    private void maxminLevel(Manager[] managers, Pair<Employee> result) {

        minmaxLevel(managers,result);
        //交换最小和最大 以前都是 first min second max
        swap(result);
    }

    //因为不能 ？ t = new T 这种形式 但是嵌套一层 泛型方法就可以使用
    private void swap(Pair<?> result) {
        swapHelper(result);
    }

    private  <T> void swapHelper(Pair<T> result){
        T t = result.getFirst();
        result.setFirst(result.getSecond());
        result.setSecond(t);
    }

    private void minmaxLevel(Manager[] managers, Pair<Employee> result) {

        if(managers.length !=0){
            Manager min = managers[0];
            Manager max = managers[1];
            for (int i = 0; i < managers.length; i++) {
                if(min.getSalary()>managers[i].getSalary()){
                    min = managers[i];
                }
                if(max.getSalary()<managers[i].getSalary()){
                    max = managers[i];
                }
            }
            result.setFirst(min);
            result.setSecond(max);
        }
    }

    private void printBuddies(Pair<? extends Employee> buddies) {
        Employee first = buddies.getFirst();
        Employee second = buddies.getSecond();
        System.out.println(first + "  and " + second);
    }


    @AllArgsConstructor
    @Data
    public class Employee {

        private String name;
        private int salary;

    }


    @Data
    public class Manager extends Employee {
        private String level;

        public Manager(String name, int salary, String level) {
            super(name, salary);
            this.level = level;
        }
    }

}
