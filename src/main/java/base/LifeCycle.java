package base;

public class LifeCycle {
    // 静态属性
    private static String staticField = getStaticField();
    // 静态方法块
    static {
        System.out.println(staticField);
        System.out.println("静态方法块初始化");
        System.out.println("Static Patch Initial");
    }
    // 普通属性
    private String field = getField();
    // 普通方法块
    {
        System.out.println(field);
        System.out.println("普通方法块初始化");
        System.out.println("Field Patch Initial");
    }
    // 构造函数
    public LifeCycle() {
        System.out.println("构造函数初始化");
        System.out.println("Structure Initial ");
    }

    public static String getStaticField() {
        String statiFiled = "Static Field Initial";
        System.out.println("静态属性初始化");
        return statiFiled;
    }

    public static String getField() {
        String filed = "Field Initial";
        System.out.println("普通属性初始化");
        return filed;
    }   
    /**
     * 静态属性初始化
     * Static Field Initial
     * 静态方法块初始化
     * Static Patch Initial
     * 普通属性初始化
     * Field Initial
     * 普通方法块初始化
     * Field Patch Initial
     * 构造函数初始化
     * Structure Initial
     *
     * 可见静态属性静态方法块只初始化一次
     *
     * 普通属性初始化
     * Field Initial
     * 普通方法块初始化
     * Field Patch Initial
     * 构造函数初始化
     * Structure Initial
     */
    public static void main(String[] argc) {
        new LifeCycle();
        new LifeCycle();
    }
}