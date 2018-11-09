package nio;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author LiuMengKe
 * @create 2018-11-09-15:00
 * 测试 nio buffer 感受nio基本用法
 */
public class NioBufferTest extends BasicNio {


    /**
     * 测试api 使用BUFFER 读取数据
     * 把数据写入buffer；
     * 调用flip；
     * 从Buffer中读取数据；
     * 调用buffer.clear()或者buffer.compact()
     *
     * ByteBuffer
     * MappedByteBuffer
     * CharBuffer
     * DoubleBuffer
     * FloatBuffer
     * IntBuffer
     * LongBuffer
     * ShortBuffer
     * 基本数据类型对应有BUFFER
     */
    @Test
    public void testBufferReadAndWrite() {
        try {
            FileChannel fileChannel = getSourceFileChannel("source.txt");
            ByteBuffer readBuffer = ByteBuffer.allocate(BUF_SIZE);
            //测试从channel写入数据到buffer
            while ((fileChannel.read(readBuffer)) != -1){
                //从写模式切换到读模式
                readBuffer.flip();
                //Charset 指定编码格式进行转换
                Charset cs = Charset.forName ("UTF-8");
                CharBuffer charBuffer = cs.decode(readBuffer);
                while (charBuffer.hasRemaining()){
                    System.out.print(charBuffer.get());
                }
                //每次读取完毕清空buffer
                readBuffer.clear();
            }

            //测试buffer 写入到channel
            FileChannel fileChannel2 = getSourceFileChannel("target.txt");
            //BufferOverflowException 缓存设置为1的时候 如果一次向buffer中put超过字节数限制的字节抛出BufferOverflowException
            ByteBuffer writeBuffer = ByteBuffer.allocate(1);
            byte[] writebytes = "测试niobuffer 追加\n系一行".getBytes("UTF-8");
            for (byte writebyte : writebytes) {
                writeBuffer.put(writebyte);
                writeBuffer.flip();
                while (writeBuffer.hasRemaining()){
                    fileChannel2.write(writeBuffer);
                }
                writeBuffer.clear();
            }
            fileChannel.close();
            fileChannel2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
