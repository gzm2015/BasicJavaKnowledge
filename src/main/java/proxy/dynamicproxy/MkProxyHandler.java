package proxy.dynamicproxy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author LiuMengKe
 * @create 2018-11-06-15:17
 */
@AllArgsConstructor
@Data
public class MkProxyHandler<T> implements InvocationHandler {
    /**
     * 被代理对象
     */
    private T targetObj;

    //proxy the proxy instance that the method was invoked on
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //com.sun.proxy.$Proxy4 所有代理类都拓展自Proxy类内部只有一个实例域即调用处理器
        Class proxyClass = proxy.getClass();
        System.out.println("invoke proxy参数"+proxyClass.getName());
        //所有代理类覆盖了Object 类的toString equal hsahCode方法
        System.out.println("before");
        Object result = method.invoke(targetObj,args);
        System.out.println("after");
        return result;
    }

    //获取代理对象
    public T getProxyTarget(){
        T result  = (T)Proxy.newProxyInstance(targetObj.getClass().getClassLoader(),targetObj.getClass().getInterfaces(),this);
        return result;
    }
}
