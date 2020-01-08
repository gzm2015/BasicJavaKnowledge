package reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lmk
 * @Date: 2020/1/8   16:12
 * @Description: 测试java 强 软 弱 虚 四种引用
 */
public class ReferenctTest {

    @Data
    @AllArgsConstructor
    @ToString
     static class User {
        private String name;
        private Integer age;

        // 覆盖finalize()方法
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("user obj is gc");
        }
    }

    @Data
    @AllArgsConstructor
     static class Person {
        private String name;
        private Long age;
    }

    /**
     * 消耗内存
     */
    public static void wasteMemory() {

        List list = new ArrayList();
        while (true) {
            list.add(new Person("kk", 1L));
        }
    }
}
