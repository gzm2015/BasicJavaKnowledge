package base;

import org.junit.Test;

/**
 * @Author:lmk
 * @Date: 2020/4/24   10:57
 * @Description:
 */
public class WeiYunSuan {
    @Test
    public void test(){
        System.out.println((1<<16)-1);
        System.out.println((1<<16));
        int result = 3&((1<<16)-1);
        System.out.println(result);
    }

    @Test
    public void test2(){
        System.out.println(10<<1);
    }

    @Test
    public void test3(){
        System.out.println(Integer.toBinaryString(-5));
        System.out.println(-5>>2);
        //右移>> ：该数对应的二进制码整体右移，左边的用原有标志位补充，右边超出的部分舍弃。
        System.out.println(Integer.toBinaryString(-5>>2));
        //无符号右移 >>> ：不管正负标志位为0还是1，将该数的二进制码整体右移，左边部分总是以0填充，右边部分舍弃。
        System.out.println(-5>>>2);
        System.out.println(Integer.toBinaryString(-5>>>2));
    }

    @Test
    public void test4(){
        int a = 2;
        int b = 1;
        int c = 5;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(Integer.toBinaryString(c));
        System.out.println(a|=b); // a|=b 相当于 a和b进行或的位运算再赋值给a a = a|b
        System.out.println(c|=a);
    }
}
