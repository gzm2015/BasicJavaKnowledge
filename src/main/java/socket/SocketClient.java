package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 测试socket 双向通信
 * @author gzm2015
 * @create 2018-11-06-13:22
 */
public class SocketClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 55533;
        InputStream inputStream = null;
        try(    Socket socket = new Socket(host,port);
                OutputStream outputStream = socket.getOutputStream()) {
            outputStream.write("客户端发动消息".getBytes("UTF-8"));
            //不应该使用关闭流的方式发送消息
            socket.shutdownOutput();
            inputStream = socket.getInputStream();
            int len ;
            byte[] bytes = new byte[1024];
            StringBuilder builder = new StringBuilder();
            while ((len = inputStream.read(bytes))!=-1){
                builder.append(new String(bytes,0,len,"UTF-8"));
            }
            System.out.println(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
