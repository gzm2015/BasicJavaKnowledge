package io;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author gzm2015
 * @create 2018-11-06-10:01
 *
 * RandomAccessFile 强大的地方在于可以直接操作指针
 * 实际上RandomAccessFile是NIO的类
 *
 */
public class RandomAccessFileTest extends BasicIO{

    /**
     * 能够直接操作指针时遇见大文件进行追加操作时可以直接在末尾指针追加
     * 避免拷贝时占用过多资源
     */
    @Test
    public void testRandomAccessFile() {
        try {
            RandomAccessFile file = new RandomAccessFile(getSourcePath("source.txt"),"rw");
            //直接将指针偏移到最后的位置
            file.seek(file.length());
            file.write("我是追加的 \r\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
