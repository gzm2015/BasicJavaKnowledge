package nio.nio_unblock_client_server;

import nio.BasicNio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

public class NioNoBlockServer extends BasicNio {

    public static void main(String[] args) {
        try {

            //注册选择器
            Selector selector = Selector.open();
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(8080));
            //将通道注册到选择器上，指定接收“监听通道”事件
            server.register(selector,SelectionKey.OP_ACCEPT);

            // 5. 轮训地获取选择器上已“就绪”的事件--->只要select()>0，说明已就绪 会在selector上一直阻塞
            while (selector.select()>0){
                // 6. 获取当前选择器所有注册的“选择键”(已就绪的监听事件)
                Iterator<SelectionKey> iterator  = selector.selectedKeys().iterator();
                // 7. 获取已“就绪”的事件，(不同的事件做不同的事)
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    // 接收事件就绪
                    if(selectionKey.isAcceptable()){

                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        // 8.2 注册到选择器上-->拿到客户端的连接为了读取通道的数据(监听读就绪事件)
                        client.register(selector,SelectionKey.OP_READ);
                        System.out.println("服务器连接成功 获取客户端连接hashcode"+client.hashCode());
                    }else if(selectionKey.isReadable()){ // 读事件就绪

                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        client.configureBlocking(false);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
                        // 9.2得到文件通道，将客户端传递过来的图片写到本地项目下(写模式、没有则创建)
                        FileOutputStream fileOutputStream = new FileOutputStream(getSourcePath("test2.jpg"));
                        FileChannel fileChannel = fileOutputStream.getChannel();

                        System.out.println("服务器收到图片了 监听连接客户端hashcode"+client.hashCode());

                        //socket channel 接受完数据以后可能一直读到0 程序就在这里阻塞
                        while (client.read(byteBuffer)>0){
                            System.out.println("读取图片");
                            byteBuffer.flip();
                            fileChannel.write(byteBuffer);
                            byteBuffer.clear();
                        }
                        fileChannel.close();
                        fileOutputStream.close();
                        System.out.println("返回图片信息");
                        //接受到图片以后返给服务端消息表示接受到了
                        byteBuffer.clear();
                        byteBuffer.put("接受到了".getBytes());
                        byteBuffer.flip();
                        client.write(byteBuffer);
                        byteBuffer.clear();
                        client.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
