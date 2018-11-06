package rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @author LiuMengKe
 * @create 2018-11-06-16:16
 */
public class RpcCustomer<T> {

    private static Object customer(String interfactName,String methodName,Class<?>[] paramTypes,Object[] arguments){
        Socket socket = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            socket = new Socket("127.0.0.1", 22233);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeUTF(interfactName);
            outputStream.writeUTF(methodName);
            outputStream.writeObject(paramTypes);
            outputStream.writeObject(arguments);
            inputStream = new ObjectInputStream(socket.getInputStream());
            return inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

    public T getProxyTarget(Class serviceClz){
        return (T) Proxy.newProxyInstance(serviceClz.getClassLoader(), serviceClz.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return customer(serviceClz.getName(),method.getName(),method.getParameterTypes(),args);
            }
        });
    }
}
