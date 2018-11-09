package nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author LiuMengKe
 * @create 2018-11-09-14:27
 */
public class BasicNio {
    /**
     * 用手写rpc框架部分举例
     * 服务器部分 RpcProvider 使用线程池技术 对不同的连接使用线程创建不同的socket
     * 并且在调用 serverSocket.accept() 程序会被阻塞
     * 一个线程创建的消耗大约在 512k - 1M 当大量连接接入的时候 JVM分配的内存都会被消耗殆尽
     */

    static int BUF_SIZE = 1024;

    public static String getSourcePath(String fileName) throws IOException {
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        return courseFile+"\\src\\main\\resources\\"+fileName;
    }

    protected FileChannel getSourceFileChannel(String s) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(getSourcePath(s), "rw");
        return randomAccessFile.getChannel();
    }


}
