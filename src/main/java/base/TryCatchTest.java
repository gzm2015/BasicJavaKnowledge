package base;

import org.junit.Test;

public class TryCatchTest {

    @Test
    public void tryCatchTest() {
        String result = tryMethodFinalReturn();
        System.out.println(result);
        String result2 = tryMethodCatchReturn();
        System.out.println(result2);
    }

    /**
     * try----------
     * catch----------
     * finally----------
     * C
     */
    private String tryMethodFinalReturn() {
        String result = "";
        try {
            System.out.println("try----------");
            show();
            result = "A";
            return result;
        } catch (Exception e) {
            System.out.println("catch----------");
            result = "B";
            return result;
        } finally {
            System.out.println("finally----------");
            result = "C";
            return result;
        }
    }


    /**
     * try----------
     * catch----------
     * finally----------
     * B
     */
    private String tryMethodCatchReturn() {
        String result = "";
        try {
            System.out.println("try----------");
            show();
            result = "A";
            return result;
        } catch (Exception e) {
            System.out.println("catch----------");
            result = "B";
            return result;
        } finally {
            System.out.println("finally----------");
            result = "C";
        }
    }


    public void show() throws Exception {
        throw new Exception();
    }
}
