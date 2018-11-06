package rpc;

/**
 * @author LiuMengKe
 * @create 2018-11-06-17:17
 * 一个简单的rpc 框架实现 来自分布式原理与实践
 */
public class SimpleRpcTest {

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RpcProvider.provider();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        RpcCustomer<EchoService> rpcCustomer = new RpcCustomer<EchoService>();
        EchoService echoService =rpcCustomer.getProxyTarget(EchoServiceImpl.class);
        echoService.echo("test link ");
    }



}
