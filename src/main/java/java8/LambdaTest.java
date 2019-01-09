package java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author LiuMengKe
 * @create 2018-12-25-15:58
 */
public class LambdaTest {


    private int lambdavariable =1;
    private static int lambdavariable2 =2;

    @Test
    public void interfaceMethod() {

        LambdaInterfaceImpl lambdaInterfaceImpl = new LambdaInterfaceImpl(new LambdaInterface() {
            @Override
            public void method1(String param) {
                System.out.println(method2(param));
            }
        });
        lambdaInterfaceImpl.testLambda("    lambda  ");

        final int localvariable = 5;
        int localvariable2 = 5;

        //lambda格式 （参数）-> {语句体}
        //接口中默认方法method2()实现无法被lambda访问到
        //可以访问局部变量 但是局部变量必须被final修饰没有修饰也能访问的隐藏被修饰如果在lambda外部修改了数据则会报错
        //可以访问所在类的成员变量 和 静态变量
        LambdaInterfaceImpl lambdaInterfaceImpl2 = new LambdaInterfaceImpl((param)-> {
            System.out.println(param);
            System.out.println("打印final修饰局部变量  "+localvariable);
            System.out.println("打印局部变量  "+localvariable2);
            System.out.println("打印成员变量  "+lambdavariable);
            System.out.println("打印静态变量  "+lambdavariable2);
        });
        //localvariable2 = 6;
        lambdaInterfaceImpl2.testLambda("lambda  ");


    }


    /**
     * 测试方法引用::
     * object::instance method 对象实例方法引用
     * Class::static method 类静态方法引用
     * Class::instance method 类实例方法引用 第一个参数会成为方法的目标 String compareToIgnoreCase String::compareToIgnoreCase 等价于 （x,y)->x.compareToIgnoreCase(y)
     */
    //::可以用于引用对象和静态方法
    @Test
    public void methodRefrence() {

        Convert<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123

        Something something = new Something();
        Convert<String, String> converter2 = something::startsWith;
        String converted2 = converter2.convert("Java");
        System.out.println(converted2);    // "J"

        Comparator<String> comparator = String::compareToIgnoreCase;
        String[] arrays = {"C","a","b"};
        Arrays.sort(arrays,comparator);
        System.out.println(Arrays.toString(arrays));

        TreeSet<String> treeSet = new TreeSet<String>(String::compareToIgnoreCase);
    }


    /**
     * 测试构造器引用
     * 会根据具体情况调用合适的构造器
     */
    @Test
    public void classRefrenceTest() {
        Arrays.asList("name1","name2","name3").stream().map(Person::new).forEach(System.out::println);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @ToString
    public class Person{
        private String name;
    }


    //自定义函数式接口
    @FunctionalInterface
    public interface Convert<F,T>{
        public T convert(F f);
    }



    class Something {
        String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }



    @AllArgsConstructor
    public class LambdaInterfaceImpl{

        private LambdaInterface lambdaInterface;

        public void testLambda(String param){
            lambdaInterface.method1(param);
        }

    }



    public interface LambdaInterface{
        public void method1(String param);
        //java8 中方法中直接写实现的方式 需要使用default类型修饰
        default String method2(String param){
            return "method2 invoke"+param;
        }
    }



}
