package base;

import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalTest {

    public  static boolean bg;
    /**
     * @author verbrennung
     * BigDecimal两种方式构建的区别
     * BigDecimal b2 = new BigDecimal(double b);
     * 方式计算的结果是一个近似值
     * BigDecimal b2 = new BigDecimal(String  b);
     * 结果是拼接成的是精确值
     */
    @Test
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("0.2");
        BigDecimal a2 = new BigDecimal("0.2");
        BigDecimal b2 = new BigDecimal(0.1);
        BigDecimal b3 = new BigDecimal(0.1);
        System.out.println(a);
        //0.2000000000000000111022302462515654042363166809082031250
        System.out.println(b2.add(b3));
        //0.4
        System.out.println(a2.add(a));
        System.out.println(a2.add(b2));
        System.out.println(b2.multiply(a2));


        System.out.println(1_000_000);
        int n;
        //System.out.println(n); java 不会对局部变量进行初始化 实例变量会在默认的无参构造函数的时候进行一次初始化
        System.out.println(bg);
    }


}
