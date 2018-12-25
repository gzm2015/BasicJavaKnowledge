package proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author LiuMengKe
 * @create 2018-12-14-16:27
 */
public class CglibTest {

    //一  enhancer.setSuperclass(RequestImpl.class);注入实际的类
    //二 clallback 里面要invokesupperclass
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RequestImpl.class);
        enhancer.setCallback(new RequestCallBack());
        RequestService service = (RequestService)enhancer.create();
        service.request();
    }

}
