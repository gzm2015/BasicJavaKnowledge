package base;

import lombok.AllArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

public class EnumTest {

    @Test
    public void testEnum(){

        //error AlarmType alarm = AlarmType.valueOf("关机");
        AlarmType alarm =AlarmType.valueOf("SHUTDOWN");
        System.out.println(alarm.getDesc());

    }
    @AllArgsConstructor
    public enum AlarmType {
        //sos 运动 静止 关机 电子围栏
        STATIC("静止",0),MOVING("运动",1),SHUTDOWN("关机",3), SOS("SOS",4),FENCE("电子围栏",5);

        private String desc;
        private Integer value;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }



    @Test
    public void test2(){
        /**
         * 测试枚举的values()
         *
         */
        String s = Color.getValue(0).getDesc();
        System.out.println("获取的值为:"+ s);



        /**
         * 测试枚举的valueof,里面的值可以是自己定义的枚举常量的名称
         * 其中valueOf方法会把一个String类型的名称转变成枚举项，也就是在枚举项中查找字面值和该参数相等的枚举项。
         */

        Color color =Color.valueOf("GREEN");
        System.out.println(color.getDesc());

        /**
         * 测试枚举的toString()方法
         */

        Color s2 = Color.getValue(0) ;
        System.out.println("获取的值为:"+ s2.toString());
    }

    public enum Color {

        RED(0,"红色"),
        BLUE(1,"蓝色"),
        GREEN(2,"绿色"),

        ;

        //    可以看出这在枚举类型里定义变量和方法和在普通类里面定义方法和变量没有什么区别。唯一要注意的只是变量和方法定义必须放在所有枚举值定义的后面，否则编译器会给出一个错误。
        private int code;
        private String desc;

        Color(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        /**
         * 自己定义一个静态方法,通过code返回枚举常量对象
         * @param code
         * @return
         */
        public static Color getValue(int code){

            for (Color  color: values()) {
                if(color.getCode() == code){
                    return  color;
                }
            }
            return null;

        }


        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }


    @Test
    public void test(){
        int a = 30;
        int b = 40;
        float  percent =  (float)(a-b)/b;
        System.out.println(String.format("%.2f",percent));
        System.out.println();

        int old = 1;
        int latest = 4;
        float  percent2 =  ((float)(latest-old)/old)*100;
        //todo 遇见只有一位小数的时候会报错
        String[] dod = String.valueOf(percent2).split("\\.");
        System.out.println(dod);
    }



    @Test
    public void test33(){
        boolean apb = RequestClient.valueOf("PC").equals(RequestClient.PC);
        Assert.assertTrue(apb);

        //RequestClient client = RequestClient.valueOf("wwc");


    }

    public enum RequestClient {
        PC,
        ANDROID,
        IOS,
        DEVICE;




    }

}
