package nio.nio_unblock_client_server;


import nio.BasicNio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

/**
 * nio阻塞式客户端联系 验证是阻塞发送的方式
 */
public class NioNoBlockClient extends BasicNio {

    public static void main(String[] args) {
        try {

            //获取socket通道
            SocketChannel socketChannel = SocketChannel.open();

            socketChannel.configureBlocking(false);
            //获得通道管理器
            Selector selector=Selector.open();

            //客户端连接服务器，需要调用channel.finishConnect();才能实际完成连接。
            socketChannel.connect(new InetSocketAddress("localhost",8080));
            //为该通道注册SelectionKey.OP_CONNECT事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            System.out.println("SocketChannel channel地址======"+socketChannel.hashCode());

            while (true){
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if(selectionKey.isConnectable()){
                        SocketChannel channel = (SocketChannel)selectionKey.channel();
                        //正在完成连接等连接完成
                        if(channel.isConnectionPending()){
                            channel.finishConnect();
                        }
                        channel.configureBlocking(false);
                        System.out.println("连接成功");
                        System.out.println("连接channel地址======"+channel.hashCode());
                        //给服务器传个图片
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
                        FileInputStream fileInputStream = new FileInputStream(getSourcePath("testPic.jpg"));
                        FileChannel imageChannel = fileInputStream.getChannel();
                        while (imageChannel.read(byteBuffer)!=-1){
                            byteBuffer.flip();
                            channel.write(byteBuffer);
                            byteBuffer.clear();
                        }
                        fileInputStream.close();
                        imageChannel.close();
                        System.out.println("发送完成");
                        channel.register(selector,SelectionKey.OP_READ);
                    }else if(selectionKey.isReadable()){
                        System.out.println("收到服务器返回信息");
                        SocketChannel channel = (SocketChannel)selectionKey.channel();
                        System.out.println("返回信息channel地址======"+channel.hashCode());
                        ByteBuffer bufferInLoop = ByteBuffer.allocate(1024);
                        if (channel.read(bufferInLoop)!=-1){
                            bufferInLoop.flip();
                            byte[] bytes = new byte[bufferInLoop.limit()];
                            bufferInLoop.get(bytes);
                            System.out.println(new String(bytes,0,bufferInLoop.limit()));
                            bufferInLoop.clear();
                        }
                        channel.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
