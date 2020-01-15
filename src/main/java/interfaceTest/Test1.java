package interfaceTest;

public interface Test1 {
    //使用default标记提供默认实现
    default void name(){
        System.out.println("");
    }
}
