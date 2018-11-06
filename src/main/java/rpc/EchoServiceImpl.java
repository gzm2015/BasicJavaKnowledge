package rpc;

/**
 * @author LiuMengKe
 * @create 2018-11-06-17:05
 */
public class EchoServiceImpl implements EchoService{
    @Override
    public void echo(String param) {
        param = param == null?"param is null":param+"   echo";
        System.out.println(param);
    }
}
