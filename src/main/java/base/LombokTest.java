package base;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

public class LombokTest {

    @Test
    public void testKK(){
        testNull(new Demo());
        //Assert.assertNull(demo.getNumber());
    }

    private void testNull(Demo demo) {
        if(demo==null)
            demo = new Demo();
        Integer number = demo.getNumber();
        if(number!=null){
            System.out.println("ffffffffff");
        }
    }


    @Data
    class Demo{
        private Integer number;
    }
}
