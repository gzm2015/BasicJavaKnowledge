package base;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

/**
 * 对象拷贝测试
 * 浅拷贝深度拷贝
 * @author gzm2015
 * @create 2018-12-29-9:55
 */
public class CloneTest {
    
    /**
     * 浅拷贝测试
     */
    @Test
    public void cloneTest() {
        Customer customer = new Customer();
        Provider provider = new Provider("aaa");
        customer.setProvider(provider);
        try {
            Provider providerClone = (Provider)provider.clone();
            Customer customerClone =(Customer) customer.clone();
            //对象浅拷贝后引用向不同的地址
            Assert.assertNotEquals(providerClone,provider);
            Assert.assertNotEquals(customerClone,customer);
            //对象中的引用对象不会被浅拷贝复制 引用的还是以前的对象的地址
            Assert.assertEquals(customer.getProvider(),customerClone.getProvider());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Provider implements Cloneable{
        private String name;
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Customer implements Cloneable{
        private Provider provider;
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    /**
     * 深拷贝测试
     * 序列化后再反序列化回来
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class User  implements Serializable {
        private static final long serialVersionUID = -795285796390052673L;
        private String name;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User2  implements Serializable {
        private static final long serialVersionUID = -795285796390052673L;
        private String name;
        private Tool tool;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tool  implements Serializable {
        private static final long serialVersionUID = -795285796390052673L;
        private String name;
    }
    /**
     * 由于User是非静态内部类持有外部类CloneTest的全部引用$this0而外部CloneTest没有序列化所以会报错 NotSerializableException
     * 如果一个可序列化的对象包含对某个不可序列化的对象的引用，那么整个序列化操作将会失败，并且会抛出一个NotSerializableException.
     *
     * 非静态内部类拥有对外部类的所有成员的完全访问权限，包括实例字段和方法。为实现这一行为，非静态内部类存储着对外部类的实例的一个隐式引用。序列化时要求所有的成员变量是Serializable,现在外部的类并没有implements Serializable,所以就抛出java.io.NotSerializableException异常。
     */
    @Test
    public void testDeep(){


        try(ByteOutputStream outputStream = new ByteOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ByteInputStream inputStream = new ByteInputStream(outputStream.getBytes(),outputStream.getCount());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ){
            User user = new User();
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            User userClone = (User)objectInputStream.readObject();

            System.out.println(user);
            System.out.println(userClone);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /**
     * 深拷贝的时候使用ByteArrayOutputStream比较方便
     * 拷贝出来的对象 和对象的引用对象都指向了新的地址
     */
    @Test
    public void testDeep2(){
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ){
            User2 user = new User2();
            Tool tool = new Tool();
            user.setTool(tool);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();

            ByteInputStream inputStream = new ByteInputStream(outputStream.toByteArray(),outputStream.size());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            User2 userClone = (User2)objectInputStream.readObject();

            Assert.assertNotEquals(user,userClone);
            Assert.assertNotEquals(user.getTool(),userClone.getTool());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
