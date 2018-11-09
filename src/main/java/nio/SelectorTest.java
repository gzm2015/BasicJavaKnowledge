package nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.*;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * @author LiuMengKe
 * @create 2018-11-09-15:53
 */
public class SelectorTest extends BasicNio{

    
    /**
     * Selector是Java NIO中的一个组件，
     * 用于检查一个或多个NIO Channel的状态是否处于可读、可写。
     * 如此可以实现单线程管理多个channels,也就是可以管理多个网络链接。
     * SocketChannelChannel SelectChannel 都是 SelectableChannel子类
     */
    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            //Channel必须是非阻塞的。所以FileChannel不适用Selector，因为FileChannel不能切换为非阻塞模式
            //FileChannel channel = new SelectorTest().getSourceFileChannel("source.txt");
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            SocketChannel socketChannel = SocketChannel.open();
            //channel 像select注册
            serverSocketChannel.register(selector,SelectionKey.OP_READ);
            socketChannel.register(selector,SelectionKey.OP_READ);
            for(;;){
                //select 方法 返回 已更新其准备就绪操作集的键的数目，该数目可能为零
                int alreadyChannel = selector.select();
                if (alreadyChannel==0){
                    continue;
                }
                //在调用select并返回了有channel就绪之后，可以通过选中的key集合来获取channel，这个操作通过调用
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectedKeys.iterator();
                while (selectionKeyIterator.hasNext()){
                    SelectionKey key = selectionKeyIterator.next();
                    if(key.isAcceptable()) {
                        key.channel();
                        // a connection was accepted by a ServerSocketChannel.
                    } else if (key.isConnectable()) {
                        // a connection was established with a remote server.
                    } else if (key.isReadable()) {
                        // a channel is ready for reading
                    } else if (key.isWritable()) {
                        // a channel is ready for writing
                    }
                    selectionKeyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
