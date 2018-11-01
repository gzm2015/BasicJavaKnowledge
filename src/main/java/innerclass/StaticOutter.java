package innerclass;

/**
 * @author LiuMengKe
 * @create 2018-11-01-11:16
 */
public class StaticOutter {

    private String a = "a";
    private static String b = "b";

    static class StatticInner {

        public void print() {
            //静态内部类不能调用外部类的非静态变量
            //System.out.println(a);
            System.out.println(b);
        }

    }

}
