package io;

import org.junit.Test;

import java.io.*;

/**
 * @author LiuMengKe
 * @create 2018-11-06-10:20
 */
public class FileStreamTset extends BasicIO{


    @Test
    public void testFileInputStream() {
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

    @Test
    public void testFileOutputStream() {
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
