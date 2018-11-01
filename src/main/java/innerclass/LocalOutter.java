package innerclass;

/**
 * @author LiuMengKe
 * @create 2018-11-01-10:58
 * 局部内部类
 */
public class LocalOutter {


    /*不用final修饰param无法通过编译*/
    public void get(final String param2){
        final String param1 = "aaa";
        class LocalInner{
            public void print(){
                System.out.println(param1+param2);
            }
        }
        new LocalInner().print();
    }
    private int age = 12;
    public void Print(int x) {
        class In {
            public void inPrint() {
                System.out.println(x);
                System.out.println(age);
            }
        }
        new In().inPrint();
    }

    public void test(int b) {
        final int a = 10;
        //这里定义匿名内部类线程并启动 同样也是内部类
        new Thread(){
            @Override
            public void run() {
                System.out.println(a);
                System.out.println(b);
            };
        }.start();
    }

    /*上面的内部类中不使用final修饰局部变量中的局部变量 在jdk8中正常 jdk7中就不行*/
    public static void main(String[] args) {
        LocalOutter outter = new LocalOutter();
        outter.get("111");
        outter.Print(1);
        outter.test(10);
    }

}
