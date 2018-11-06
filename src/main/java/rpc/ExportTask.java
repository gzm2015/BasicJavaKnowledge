package rpc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author LiuMengKe
 * @create 2018-11-06-16:43
 */
@Data
@AllArgsConstructor
public class ExportTask implements Runnable {
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
            Class<?> interfaceClz = Class.forName(interfactName);
            String methodName = objectInputStream.readUTF();
            Class<?>[] paramTypes = (Class<?>[])objectInputStream.readObject();
            Object[] arguments =  (Object[])objectInputStream.readObject();
            Method method = interfaceClz.getMethod(methodName,paramTypes);
            Object target = method.invoke(interfaceClz.newInstance(),arguments);
            //反射获取被代理对象以后对象流输出
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(target);
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
