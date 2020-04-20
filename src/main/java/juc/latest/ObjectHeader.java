package juc.latest;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author:lmk
 * @Date: 2020/4/20   15:00
 * @Description:
 */
public class ObjectHeader {
    public static void main(String[] args) {
        Object object = new Object();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }
}

class ObjectHeader2 {
    public static void main(String[] args) {
        //求hashcode以后会放在对象头中
        Object object = new Object();
        object.hashCode();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }
}