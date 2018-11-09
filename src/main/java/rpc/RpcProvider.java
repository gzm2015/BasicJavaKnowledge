package rpc;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LiuMengKe
 * @create 2018-11-06-16:15
 * 按照客户端和服务端的约定接受请求 并且返回对象
 */
public class RpcProvider {


    public static void provider() throws Exception {
        //线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ServerSocket serverSocket = null;
        try{
            while (true){
                serverSocket = new ServerSocket(22233);
                executorService.submit(new ExportTask(serverSocket.accept()));
            }
        }finally {
            if(serverSocket!=null){
                serverSocket.close();
            }
        }
    }
}
