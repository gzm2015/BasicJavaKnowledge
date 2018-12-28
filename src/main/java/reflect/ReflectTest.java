package reflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * 反射api测试
 *
 * @author LiuMengKe
 * @create 2018-12-28-15:26
 */
public class ReflectTest {

    private int testint = 20;

    @Test
    public void reflect() {
        Reflect reflect = new Reflect("name");
        Class clazz = reflect.getClass();
        //Field[] fields = clazz.getFields();//直接对内部类clazz getfields 没有结果
        Field[] fields = clazz.getDeclaredFields();
        String name;
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println(field.getName());
            //此时Reflect 作为此测试的一个内部类 除了field name  还有一个this$0 this$0就是内部类所自动保留的一个指向所在外部类的引用。
            //内部对象的内部对象持有this$1 依次类推
            //查看target中 ReflectTest$Reflect 直接在构造函数上就有ReflectTest this$0
            /*
            public class ReflectTest$Reflect {
            private String name;
            @ConstructorProperties({"name"})
            public ReflectTest$Reflect(ReflectTest this$0, String name) {
                this.this$0 = this$0;
                this.name = name;
            }*/
            //这也是为什么内部类能够访问外部类的私有field 尽管编译以后是两个对立的class
            //reflect.print();
            //"this$0既然是内部类中外部类的引用自然也可以通过反射获取对应对象 此处内部类为Reflect 外部类自然为ReflectTest"
            if ("this$0".equals(field.getName())) {
                try {
                    ReflectTest reflectTest = (ReflectTest) field.get(reflect);
                    Assert.assertEquals(20, reflectTest.testint);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    name = (String) field.get(reflect);
                    System.out.println(name);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * getFields()  getDeclaredFields()  测试不同
     * getDeclaredFields 返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段。
     * getFields 返回一个包含某些 Field 对象的数组，这些对象反映此 Class 对象所表示的类或接口的所有可访问公共字段。
     * getFields 公共字段 getDeclaredFields 所有字段 只是私有字段使用的时候要field.setAccessible(true);
     */
    @Test
    public void declare() {
        ReflectSon reflectSon = new ReflectSon("name",22);
        Class clazz = reflectSon.getClass();
        Field[] fields = clazz.getFields();
        Assert.assertEquals(1,fields.length);
        Assert.assertEquals("age",fields[0].getName());
    }

    @AllArgsConstructor
    @Data
    public class Reflect {
        private String name;
        public void print() {
            System.out.println(testint);
        }
    }


    @Data
    public class ReflectSon extends Reflect {
        public int age;

        public ReflectSon(String name, int age) {
            super(name);
            this.age = age;
        }

    }


}
