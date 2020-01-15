package base;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Author:lmk
 * @Date: 2020/1/9   11:25
 * @Description:
 */
public class StringTest {
    
    /**
     * string intern方法被调用的时候 pool里面有对应string 取出对应对象的引用
     */
    @Test
    public void test2(){
        String s3 = new String("12") + new String("34");
        String s4 = "1234";
        String s5 = "12"+"34";

        System.out.println(s3==s4);
        System.out.println(s3==s5);
        System.out.println(s4==s5);

        //当s3调用intern的时候，会检查字符串池中是否含有该字符串。由于之前定义的s3已经进入字符串池中，所以会得到相同的引用
        System.out.println(s3.intern()==s4);
    }

    @Test
    public void test(){

        String str1 = "a";
        String str2 = "b";
        String str3 = "ab";
        String str4 = str1 + str2;
        String str5 = new String("ab");

        System.out.println(str5.equals(str3));
        System.out.println(str5 == str3);
        System.out.println(str5.intern() == str3);
        System.out.println(str5.intern() == str4);

    }


}
