package proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author gzm2015
 * @create 2018-12-14-16:30
 */
public class RequestCallBack implements MethodInterceptor {


    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if("request".equals(method.getName())){
            System.out.println("代理对象方法调用=========");
            //被代理对象方法调用
            Object re = methodProxy.invokeSuper(o,args);
            System.out.println("代理调用结束=========");
            return re;
        }
        return null;
    }
}
