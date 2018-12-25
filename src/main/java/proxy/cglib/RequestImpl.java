package proxy.cglib;

/**
 * @author LiuMengKe
 * @create 2018-12-14-16:28
 */
public class RequestImpl implements RequestService {

    @Override
    public void request() {
        System.out.println("系统模块方法调用 ===========ffffffff");
    }
}
