package io;

import org.junit.Test;

import java.io.*;

/**
 * @author LiuMengKe
 * @create 2018-11-06-9:12
 */
public class ReaderTest extends BasicIO{


    @Test
    public void fileTest() {

        FileReader reader = null;
        FileWriter writer = null;
        try {
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
            reader = new BufferedReader(new FileReader(getSourcePath("source.txt")));
            writer = new BufferedWriter(new FileWriter(getSourcePath("target.txt")));
            String str;
            /*while ((len = reader.read(buf))!=-1){
                writer.write(buf,0,len);
            }*/
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
