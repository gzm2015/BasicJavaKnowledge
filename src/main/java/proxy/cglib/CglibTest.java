package proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author LiuMengKe
 * @create 2018-12-14-16:27
 */
public class CglibTest {

    //一  enhancer.setSuperclass(RequestServiceImpl.class);注入实际的类
    //二 clallback 里面要invokesupperclass
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RequestServiceImpl.class);
        enhancer.setCallback(new RequestCallBack());
        RequestServiceImpl service = (RequestServiceImpl)enhancer.create();
        service.request();
    }

}
