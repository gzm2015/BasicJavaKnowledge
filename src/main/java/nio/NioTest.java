package nio;


import io.BasicIO;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


/**
 * nio 特性练习
 * https://juejin.im/post/5af942c6f265da0b7026050c
 */
public class NioTest extends BasicNio {


    /**
     * buffer基本特性测试
     * position capacity limit
     */
    @Test
    public void bufferTest(){

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);

        System.out.println("初始位置=================");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        byteBuffer.put("abcd".getBytes());
        System.out.println("写入后=================");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        //注意要先flip重置了limit position以后 再去设置数组
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.limit()];
        System.out.println("flip后=================");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("读取后=================");
        byteBuffer.get(bytes);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());


        System.out.println(new String(bytes,0,bytes.length));


    }


    @Test
    public void channelTest(){
        //channel有很多实现类 以FileChannel为例获取
        try {
            FileInputStream  fileInputStream= new FileInputStream(getSourcePath("source.txt"));
            //1.fileInputStream获取  因为nio是面向缓存的 可以类比 BufferedInputStream 构造方法里面都有 Stream
            FileChannel fileChannel = fileInputStream.getChannel();
            //2.jdk7 以后直接获取
            FileChannel fileChannel2 = FileChannel.open(Paths.get("source.txt"), StandardOpenOption.WRITE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 练习使用nio实现复制功能
     */
    @Test
    public void testCopy(){

        try {
            FileInputStream  fileInputStream= new FileInputStream(getSourcePath("source.txt"));
            FileChannel inputChannel = fileInputStream.getChannel();

            //FileInputStream  fileOutputStream= new FileInputStream(getSourcePath("target.txt"));
            //注意这里必须是outputstream
            FileOutputStream  fileOutputStream= new FileOutputStream(getSourcePath("target.txt"));
            FileChannel outChannel = fileOutputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(50*1024);

            while (inputChannel.read(buffer)!=-1){
                //inputchannel将数据写入了buffer为了复制切换为可读模式
                buffer.flip();
                //outchannel从buffer读取数据写入管道
                //抛出 NonWritableChannelException 因为这里outchannel是InputStream得来
                outChannel.write(buffer);
                buffer.clear();
            }
            inputChannel.close();
            outChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testCopy2(){

        try {
            FileInputStream  fileInputStream= new FileInputStream(getSourcePath("testPic.jpg"));
            FileChannel inputChannel = fileInputStream.getChannel();

            //FileInputStream  fileOutputStream= new FileInputStream(getSourcePath("target.txt"));
            //注意这里必须是outputstream
            FileOutputStream  fileOutputStream= new FileOutputStream(getSourcePath("target.jpg"));
            FileChannel outChannel = fileOutputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(50*1024);

            while (inputChannel.read(buffer)!=-1){
                //inputchannel将数据写入了buffer为了复制切换为可读模式
                buffer.flip();
                //outchannel从buffer读取数据写入管道
                //抛出 NonWritableChannelException 因为这里outchannel是InputStream得来
                outChannel.write(buffer);
                buffer.clear();
            }
            inputChannel.close();
            outChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    /**
     * https://www.jianshu.com/p/8ae3e57a8aa9
     * 直接缓冲区 就是 在内核和用户进程同时使用虚拟内存指向通一段物理内存 这样内核对内存的操作就可以不用拷贝二被用户进程看见
     * 非直接缓冲区 就是内核和用户进程中有个拷贝的过程
     * Buffer.allocate 是操作非直接缓冲区
     * ByteBuffer.allocateDirect() 是操作直接缓冲区
     */
    
    
    /**
     * 测试直接使用直接缓冲
     */
    @Test
    public void testDirect(){
        FileInputStream  fileInputStream= null;
        FileOutputStream  fileOutputStream= null;
        try {
            fileInputStream = new FileInputStream(getSourcePath("source.txt"));
            FileChannel inputChannel = fileInputStream.getChannel();

            //这里要求目标地址必须可读可写所以不能只用outstram了
            /*fileOutputStream= new FileOutputStream(getSourcePath("target.txt"));
            FileChannel outChannel = fileOutputStream.getChannel();*/
            FileChannel outChannel = FileChannel.open(Paths.get(getSourcePath("source.txt")), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

            //对输入文件进行内存映射
            MappedByteBuffer inBuffer = inputChannel.map(FileChannel.MapMode.READ_ONLY,0,inputChannel.size());
            //对输出文件进行内存映射
            MappedByteBuffer outBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE,0,inputChannel.size());

            byte[] dst = new byte[inBuffer.limit()];
            inBuffer.get(dst);
            outBuffer.put(dst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 测试管道直接直接传输
     * 通道之间通过transfer()实现数据的传输(直接操作缓冲区)：
     */
    @Test
    public void testChannel(){
        FileInputStream  fileInputStream= null;
        FileOutputStream  fileOutputStream= null;
        try {
            fileInputStream = new FileInputStream(getSourcePath("source.txt"));
            fileOutputStream= new FileOutputStream(getSourcePath("target.txt"));
            FileChannel outChannel = fileOutputStream.getChannel();
            FileChannel inputChannel = fileInputStream.getChannel();
            inputChannel.transferTo(0,inputChannel.size(),outChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分散读取
     * channel的数据 被分散到 buffer数组中
     * source.txt中内容为: 测试nio
     * byteBuffer1  内容为 测试n
     * byteBuffer2 内容为 o
     * 这里故意让第一个个buffer内容少好讲字节分配到第二个buffer中
     * 可能出现乱码因为汉字的字节码个数是不一定的
     */
    @Test
    public void testScanner(){
        FileInputStream  fileInputStream= null;
        try {
            fileInputStream = new FileInputStream(getSourcePath("source.txt"));
            FileChannel inputChannel = fileInputStream.getChannel();
            ByteBuffer byteBuffer1 = ByteBuffer.allocate(8);
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
            ByteBuffer[] buffers = {byteBuffer1,byteBuffer2};
            inputChannel.read(buffers);
            for (ByteBuffer buffer : buffers) {
                buffer.flip();
                byte[] bytes = new byte[buffer.limit()];
                buffer.get(bytes);
                String sour  = new String(bytes,0,bytes.length);
                System.out.println(sour);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 聚集写入 将多个buffer中的内容写到同一个channel中去
     */
    @Test
    public void testGather(){
        FileInputStream  fileInputStream= null;
        FileOutputStream fileOutputStream = null;
        try{
            fileInputStream = new FileInputStream(getSourcePath("source.txt"));
            fileOutputStream = new FileOutputStream(getSourcePath("target.txt"));
            FileChannel inputChannel = fileInputStream.getChannel();
            FileChannel outChannel = fileOutputStream.getChannel();
            ByteBuffer byteBuffer1 = ByteBuffer.allocate(8);
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
            ByteBuffer[] buffers = {byteBuffer1,byteBuffer2};
            inputChannel.read(buffers);

            //注意这里outchannel要开始读buffer数组数据了 需要转换为读模式
            for (ByteBuffer buffer : buffers) {
                buffer.flip();
            }
            outChannel.write(buffers);
            inputChannel.close();
            outChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //.....
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
