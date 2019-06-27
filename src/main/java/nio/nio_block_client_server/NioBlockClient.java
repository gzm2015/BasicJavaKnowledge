package nio.nio_block_client_server;


import nio.BasicNio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * nio阻塞式客户端联系 验证是阻塞发送的方式
 */
public class NioBlockClient extends BasicNio {

    public static void main(String[] args) {
        try {
            FileChannel inputChannel = FileChannel.open(Paths.get(getSourcePath("testPic.jpg")), StandardOpenOption.READ);
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost",8080));
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (inputChannel.read(byteBuffer)!=-1){
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }


            //接受服务器端返回的信息  两边都会阻塞
            while (socketChannel.read(byteBuffer)!=-1){
                byteBuffer.flip();
                byte[] bytes = new byte[byteBuffer.limit()];
                byteBuffer.get(bytes);
                System.out.println(new String(bytes,0,byteBuffer.limit()));
                byteBuffer.clear();
            }


            inputChannel.close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
