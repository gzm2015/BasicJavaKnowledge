package io;

import java.io.*;

/**
 * @author LiuMengKe
 * @create 2018-11-05-16:55
 */
public class FileOutputStreamTest extends BasicIO{

    public static void main(String[] args) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(getSourcePath("source.txt"));
            String append = new String("appendffffffffffffffffffffffffffffff");
            outputStream.write(append.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(outputStream!=null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
