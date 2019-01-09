package innerclass;

/**
 * @author gzm2015
 * @create 2018-11-01-10:38
 * 内部类练习
 * https://www.cnblogs.com/dolphin0520/p/3811445.html
 */
public class BaseInnerClassTest {


    /**
     * java 内部类分为 成员内部类 局部内部类 匿名内部类 静态内部类 四种
     * 内部类的主要作用是解决java多继承问题 内部类持有对应外部类的指针this$0 能够直接访问其中的成员变量 同时又可以重新实现外部类的接口
     * 成员内部类 ： 必须先实例化父类才能实例化成员内部类 同时不能使用静态变量
     *             成员内部类同样可以使用 public protected default private 修饰
     *             使用private修饰的时候 无法从外部获取对象
     * 局部内部类 ： 定义在方法中 如果使用内部类以外的变量需要将变量定义为final类型 比如方法中定义的内部类是一个线程 线程使用局部变量
     *             方法结束以后 线程仍然在运行 还需要使用方法中的局部变量 所以java使用复制的办法复制了需要被使用的值
     * 匿名内部类 ： 比如创建线程时和局部内部类相似
     * 静态内部类 ： 只能用外部类中的静态成员变量 和外部类独立无续先创建外部类
     *
     * 调用方式
     *          成员内部类： MemberOutter.Inner inner = new MemberOutter().new Inner();
     *          静态内部类： StaticOuter.Inner inner = new StaticOuter.Inner();
     *
     * 总结：
     *      内部类只是一个编译前的概念 编译后实际上是两个类 比如 Outter.class Outter$Inner.class
     *      只是内部类持有外部类的指针
     */
    public static void main(String[] args) {
        //成员变量是private修饰时无法通过编译 无法在其他类获取私有构造函数
        //new MemberOutter().new MemerInner();
        StaticOutter.StatticInner inner = new StaticOutter.StatticInner();
        inner.print();
    }

}
