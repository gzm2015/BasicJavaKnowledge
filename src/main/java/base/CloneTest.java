package base;

import lombok.Data;
import org.junit.Test;

/**
 * 对象拷贝测试
 * 浅拷贝深度拷贝
 * @author LiuMengKe
 * @create 2018-12-29-9:55
 */
public class CloneTest {


    @Test
    public void cloneTest() {
        Customer customer = new Customer();
        Provider provider = new Provider();
        customer.setProvider(provider);
        try {
            Object cloneObj = provider.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }


    @Data
    public class Provider{
        private String name;
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    @Data
    public class Customer{
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
}
