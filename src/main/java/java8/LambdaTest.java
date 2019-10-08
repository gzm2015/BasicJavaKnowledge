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
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author gzm2015
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

        //对象实例方法引用
        Something something = new Something();
        Convert<String, String> converter2 = something::startsWith;
        String converted2 = converter2.convert("Java");
        System.out.println(converted2);    // "J"

        //类静态方法引用
        Convert<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123


        //类实例方法引用 可以看见这个简化方式
        Comparator<String> comparator2 = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
        Comparator<String> comparator3 = (x, y) -> x.compareTo(y);
        Comparator<String> comparator = String::compareToIgnoreCase;
        String[] arrays = {"C","a","b"};
        Arrays.sort(arrays,comparator);
        System.out.println(Arrays.toString(arrays));

        TreeSet<String> treeSet = new TreeSet<String>(String::compareToIgnoreCase);


        Consumer<String> consumer = (x)->x.toUpperCase();
        Consumer<String> consumer2 = String::toUpperCase;
        BiFunction<String, String, Integer> biFunction = (x, y) -> x.compareTo(y);
        BiFunction<String, String, Integer> biFunction2 = String::compareTo;
    }


    /**
     * 测试构造器引用
     * 会根据具体情况调用合适的构造器
     */
    @Test
    public void classRefrenceTest() {
        Arrays.asList("name1","name2","name3").stream().map(Person::new).forEach(System.out::println);
    }

    /**
     * Supplier 无输入有输出
     * Customer 有输入无输出
     * Function 有输入有输出
     * BiFunction 多个输入一个输出
     */
    @Test
    public void test5(){
        //Lambda 无参数构造方法
        Supplier<Employee> sup = () -> new Employee();
        Employee employee = sup.get();

        //构造器引用
        Supplier<Employee> sup2 = Employee :: new;
        Employee employee2 = sup2.get();

        //有一个参数构造方法
        Function<String, Employee> fun = (x) -> new Employee(x);
        Employee emp1 = fun.apply("张三");

        Function<String, Employee> fun2 = Employee :: new;
        Employee emp2 = fun2.apply("张三");

        //两个参数的构造方法
        BiFunction<String, Integer, Employee> bf = (x, y) -> new Employee(x, y);
        Employee emp3 = bf.apply("李四", 18);

        BiFunction<String, Integer, Employee> bf2 = Employee :: new;
        Employee emp4 = bf.apply("李四", 18);
        System.out.println(emp4.getName());
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




    public interface LambdaInterface{
        void method1(String param);
        //java8 中方法中直接写实现的方式 需要使用default类型修饰
        default String method2(String param){
            return "method2 invoke"+param;
        }
    }

    @AllArgsConstructor
    public class LambdaInterfaceImpl{

        private LambdaInterface lambdaInterface;

        public void testLambda(String param){
            lambdaInterface.method1(param);
        }

    }


    public class Employee {
        private String name;
        private int age;
        private double salary;

        public Employee() {

        }

        public Employee(String name) {
            this.name = name;
        }

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Employee(String name, int age, double salary) {
            super();
            this.name = name;
            this.age = age;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public double getSalary() {
            return salary;
        }
        public void setSalary(double salary) {
            this.salary = salary;
        }

    }




}
