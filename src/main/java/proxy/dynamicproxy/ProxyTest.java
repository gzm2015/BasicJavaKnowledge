package proxy.dynamicproxy;

import org.junit.Test;

/**
 * @author LiuMengKe
 * @create 2018-11-06-14:52
 * 代理测试
 * Java实现原理主要有两类 基于接口或者基于子类
 * JDK动态代理 基于接口 aop使用的就是jdk动态代理
 * Cglib 基于子类
 *
 * 动态代理的意义在于不必为每个需要进行代理的类定义一个类并持有被代理类对象复写方法
 */
public class ProxyTest {
    /**
     * 动态代理的基本使用
     */
    @Test
    public void testProxy() {
        UserService service =  new UserServiceImpl();
        MkProxyHandler<UserService> handler = new MkProxyHandler<UserService>(service);
        //UserService proxyService = (UserService)Proxy.newProxyInstance(UserService.class.getClassLoader(),new Class[] { UserService.class },handler);
        //使用泛型改造一下 获取改造后的代理对象
        UserService proxyService = handler.getProxyTarget();
        proxyService.print();
    }
}
