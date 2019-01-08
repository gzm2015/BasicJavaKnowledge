package base;

import org.junit.Assert;
import org.junit.Test;

public class TryCatchReturnTest {

    /**
     * 测试在try catch finally return值 查看finally return 值原catch中期望return值的影响
     * 结合测试得出结论 不论有没有抛出异常 catch中是否return 只要finally对值进行了覆盖并return原本return的值会被finally覆盖
     * 如果finally只是覆盖值没有return则finally覆盖值在return的时候不会返回返回的是try中的值或catch中的值
     */
    @Test
    public void tryCatchTest() {
        String result = tryMethodFinalReturn();
        Assert.assertEquals("finally location",result);
        String result2 = tryMethodCatchReturn();
        Assert.assertEquals("catch location",result2);

        String result3 = normalFinalReturn();
        Assert.assertEquals("finally location",result3);
        String result4 = normalTryReturn();
        Assert.assertEquals("try location",result4);
    }



    /**
     * 无异常 finally 没有return 不会覆盖了try return 的值
     */
    private String normalTryReturn(){
        String result = "";
        try {
            result = "try location";
            return result;
        } catch (Exception e) {
            result = "catch location";
            return result;
        } finally {
            result = "finally location";
        }
    }


    /**
     * 无异常 finally return 覆盖了try return 的值
     */
    private String normalFinalReturn(){
        String result = "";
        try {
            result = "try location";
            return result;
        } catch (Exception e) {
            result = "catch location";
            return result;
        } finally {
            result = "finally location";
            return result;
        }
    }


    /**
     * catch 中return 会被finally覆盖
     */
    private String tryMethodFinalReturn() {
        String result = "";
        try {
            throwException();
            result = "try location";
            return result;
        } catch (Exception e) {
            result = "catch location";
            return result;
        } finally {
            result = "finally location";
            return result;
        }
    }

    /**
     * catch 中return 不会被finally覆盖
     */
    private String tryMethodCatchReturn() {
        String result = "";
        try {
            throwException();
            result = "try location";
            return result;
        } catch (Exception e) {
            result = "catch location";
            return result;
        } finally {
            result = "finally location";
        }
    }


    public void throwException() throws Exception {
        throw new Exception();
    }
}
