package io;

import org.junit.Test;

import java.io.*;

/**
 * @author LiuMengKe
 * @create 2018-11-05-16:16
 */
public class FileInputStreamTest extends BasicIO{

    public static void main(String[] args) {
        InputStream inputStream = null;
        try {
            inputStream  = new FileInputStream(getSourcePath("source.txt"));
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes))!=-1){
                System.out.println(new String(bytes,0,len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
