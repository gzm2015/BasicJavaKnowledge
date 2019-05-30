package nio;


import org.junit.Test;

import java.nio.ByteBuffer;

public class NioTest {


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






}
