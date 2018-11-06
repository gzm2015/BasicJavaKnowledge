package rpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
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
