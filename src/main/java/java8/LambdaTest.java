package java8;

import lombok.AllArgsConstructor;
import org.junit.Test;

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
     * 测试函数式编程接口
     *
     */
    //::可以用于引用对象和静态方法
    @Test
    public void function() {

        Convert<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123

        Something something = new Something();
        Convert<String, String> converter2 = something::startsWith;
        String converted2 = converter2.convert("Java");
        System.out.println(converted2);    // "J"
    }


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
