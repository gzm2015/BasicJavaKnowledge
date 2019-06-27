package nio.nio_block_client_server;

import nio.BasicNio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioBlockServer extends BasicNio {

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8080));

            FileChannel fileChannel = FileChannel.open(Paths.get(getSourcePath("target.jpg")),StandardOpenOption.CREATE,StandardOpenOption.WRITE);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //阻塞的方式获取客户端连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            while (socketChannel.read(byteBuffer)!=-1){
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
                byteBuffer.clear();
            }

            //接受到图片以后返给服务端消息表示接受到了
            byteBuffer.put("接受到了".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();


            fileChannel.close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
