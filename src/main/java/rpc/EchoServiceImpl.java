package rpc;

/**
 * @author gzm2015
 * @create 2018-11-06-17:05
 */
public class EchoServiceImpl implements EchoService{
    @Override
    public void echo(String param) {
        param = param == null?"param is null":param+"   I am Ok";
        System.out.println(param);
    }
}
