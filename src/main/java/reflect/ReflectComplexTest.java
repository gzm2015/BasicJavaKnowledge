package reflect;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * @author LiuMengKe
 * @create 2018-12-28-16:42
 *
 * 综合练习 自定义注解并标记
 * 根据注解反射 设置对象值
 *
 */
public class ReflectComplexTest {


    @Test
    public void reflectAnnotation() {

        Reflect reflect = new Reflect();
        Class clazz = reflect.getClass();
        Field[] fields  = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ReflectAnnotation annoOnField = field.getAnnotation(ReflectAnnotation.class);
            if(annoOnField!=null){
               String sexDesc =  annoOnField.sex().description;
                System.out.println(sexDesc);
                try {
                    field.set(reflect,sexDesc);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Assert.assertEquals(Sex.FEMAL.description,reflect.getSex());

    }


    /*注意自定义注解的写法*/
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ReflectAnnotation{

        //注意处默认值的写法
        Sex sex() default Sex.MALE ;

    }

    public enum Sex{
        MALE("男性"),FEMAL("女性");
        private String description;
        Sex(String description) {
            this.description = description;
        }
    }

    @Data
    public class Reflect{

        @ReflectAnnotation(sex = Sex.FEMAL)
        private String sex;

    }



}
