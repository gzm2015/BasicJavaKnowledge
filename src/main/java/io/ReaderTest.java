package io;

import org.junit.Test;

import java.io.*;

/**
 * @author gzm2015
 * @create 2018-11-06-9:12
 */
public class ReaderTest extends BasicIO{


    @Test
    public void fileTest() {

        FileReader reader = null;
        FileWriter writer = null;
        try {
            //字节流的byte数组在这里换成了char
            reader = new FileReader(getSourcePath("source.txt"));
            writer = new FileWriter(getSourcePath("source.txt"));
            char[] buf = new char[BUF_SIZE];
            int len;
            while ((len = reader.read(buf))!=-1){
                writer.write(buf,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Test
    public void buffertest() {

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            //缓冲流里面都是普通流
            reader = new BufferedReader(new FileReader(getSourcePath("source.txt")));
            writer = new BufferedWriter(new FileWriter(getSourcePath("target.txt")));
            String str;
            //缓冲字符流可以直接读取一行
            while ((str = reader.readLine())!=null){
                writer.write(str);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
