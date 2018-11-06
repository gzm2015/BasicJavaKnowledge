package io;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

/**
 * 对象流
 * @author LiuMengKe
 * @create 2018-11-06-9:30
 * 1.序列化反序列化双方需要使用同样的serialVersionUID serialVersionUID不同表示版本不同
 * 2.transient修饰的属性反序列化后没有值
 * 3.static 关键字修饰的属性不能被序列化
 */
public class ObjectStreamTest extends BasicIO implements Serializable{

    private static final long serialVersionUID = -795285796390052673L;

    @Test
    public void objectStreamTest() {

        Person p = new Person();
        p.setName("jack");
        p.setPassword("123");
        ObjectOutputStream outputStream = null;
        try {
            outputStream= new ObjectOutputStream(new FileOutputStream(getSourcePath("objectout.txt")));
            outputStream.writeObject(p);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void objectStreamTest2() {

        ObjectInputStream inputStream = null;
        try {
            inputStream= new ObjectInputStream(new FileInputStream(getSourcePath("objectout.txt")));
            Person person = (Person)inputStream.readObject();
            Assert.assertEquals("jack",person.getName());
            Assert.assertEquals(null,person.getPassword());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class Person implements Serializable {

        private static final long serialVersionUID = -795285796390052673L;
        private String name;
        private transient String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }



}
