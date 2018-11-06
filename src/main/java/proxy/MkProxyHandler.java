package proxy;

import lombok.AllArgsConstructor;
import lombok.Data;

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

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
