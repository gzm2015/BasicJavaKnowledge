package rpc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gzm2015
 * @create 2018-11-06-16:15
 * 按照客户端和服务端的约定接受请求 并且返回对象
 */
public class RpcExporter {


    public static void provider(String host,int port) throws Exception {
        //线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ServerSocket serverSocket =  new ServerSocket();
        serverSocket.bind(new InetSocketAddress(host,port));
        try{
            while (true){
                executorService.execute(new ExporterTask(serverSocket.accept()));
            }
        }finally {
            if(serverSocket!=null){
                serverSocket.close();
            }
        }
    }

    @Data
    @AllArgsConstructor
    private static class ExporterTask implements Runnable {
        private Socket socket;
        @Override
        public void run()  {
            InputStream inputStream = null;
            ObjectOutputStream outputStream = null;
            //服务端监听获取信息
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                //客户端服务端约定按照 接口名 方法名 方法参数类型 方法参数值的方式依次传递
                String interfactName = objectInputStream.readUTF();
                Class<?> interfaceClz = Class.forName(interfactName);//类名
                String methodName = objectInputStream.readUTF();//方法名
                Class<?>[] paramTypes = (Class<?>[])objectInputStream.readObject();//参数类型
                Object[] arguments =  (Object[])objectInputStream.readObject();//参数值
                Method method = interfaceClz.getMethod(methodName,paramTypes);//根据方法签名获得方法
                Object result = method.invoke(interfaceClz.newInstance(),arguments);
                //反射获取被代理对象以后对象流输出
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(inputStream!=null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(outputStream!=null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(socket!=null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
