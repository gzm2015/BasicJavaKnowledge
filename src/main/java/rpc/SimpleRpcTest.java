package rpc;

import java.net.InetSocketAddress;

/**
 * @author LiuMengKe
 * @create 2018-11-06-17:17
 * 一个简单的rpc 框架实现 来自分布式原理与实践
 */
public class SimpleRpcTest {

    public static final String host = "127.0.0.1";
    public static final int port = 9999;

    public static void main(String[] args) {

        Thread provider = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RpcExporter.provider(host,port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        provider.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RpcImporter<EchoService> rpcImporter = new RpcImporter();
        EchoService echoService = rpcImporter.importer(EchoServiceImpl.class,new InetSocketAddress(host,port));
        echoService.echo("Are you Ok----> ");
    }



}
