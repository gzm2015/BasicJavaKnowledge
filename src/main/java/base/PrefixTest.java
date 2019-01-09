package base;

import org.junit.Test;

/**
 * @author gzm2015
 * @create 2018-10-21-18:03
 * idea 快捷键
 */
public class PrefixTest {

    @Test
    public void testPostFix() {
        String a = "abc";
        boolean b = true;
        assert b;
        //a.nn
        if (a != null) {

        }
        //a.try
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        int c = 100;
        //c.forr
        for (int i = c; i > 0; i--) {

        }
        //c.fori
        for (int i = 0; i < c; i++) {

        }
        //(b.not)
        if (!b) {

        }
        //a.null
        if (a == null) {

        }
        //a.notnull
        if (a != null) {

        }

        //a.field 可以将一个局部变量 变为 private 类型的 类变量


        //a.switch
        switch (a) {

        }

        //a.instanceof
        String isString = a instanceof String ? ((String) a) : null;

        //a.par ----> (a)
        //(a)

        System.out.println();
        System.out.println();
        //a.return
        //return a;
    }


}
