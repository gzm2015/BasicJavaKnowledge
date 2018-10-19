package base;

import org.junit.Test;

public class SwitchTest {

    /**
     * 注释
     **/
    @Test
    public void switchTest(){

        Sex sex = Sex.MIDDLE;
        String result = switchMethod(sex);
        System.out.println(result);;

    }

    private String switchMethod(Sex sex) {
        switch (sex){
            case MAN:
                System.out.println("man");
                return "aaaa";
            case WOMAN:
                System.out.println("woman");
                return "bbb";
            default:
                return "ccc";
        }
    }

    /**
     * 注释
     **/
    public enum Sex{
        /**
         *
         */
        MAN("男"),
        /**
         *
         */
        WOMAN("女"),
        /**
         *
         */
        MIDDLE("妖怪");
        private String description;

        Sex(String description) {
            this.description = description;
        }
    }

}
