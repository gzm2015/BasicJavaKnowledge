package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author LiuMengKe
 * @create 2018-11-06-13:29
 */
public class SocketServer {
    
    /**
     *  socket + io 是socket编程的主要构成部分
     *  主要就io的来源和去向编程了网络
     *  因为socket由应用层调用 相当于TCP/IP  协议层的封装
     *  注意socket 服务端要使用的是ServerSocket
     *  服务端要监听先accept
     */
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //注意服务器端用ServerSocket
            serverSocket = new ServerSocket(55533);
            socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            int len;
            byte[] bytes = new byte[1024];
            StringBuilder builder = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, len, "UTF-8"));
            }
            System.out.println(builder.toString());
            outputStream = socket.getOutputStream();
            outputStream.write("服务器收送消息".getBytes());
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inputStream.close();
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
