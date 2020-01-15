package interfaceTest;

/**
 * @Author:lmk
 * @Date: 2020/1/15   11:10
 * @Description:
 */
public class InterFaceTest implements Test1 {
    
    /**
     * Test1 Test2中两个接口提供了命名相同参数一致的两个方法，并且Test1中提供了默认实现那么此时
     * 必须覆写方法消除歧义性
     * 如果只实现Test1接口 Test1接口提供了默认实现则不需处理
     * 或者Test1是类而不是接口 通过超类优先原则也不用覆写
     *
     */
    @Override
    public void name() {

    }
}
