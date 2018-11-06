package io;

import java.io.*;

/**
 * @author LiuMengKe
 * @create 2018-11-05-16:55
 */
public class BufferOutputStreamTest extends BasicIO{

    public static void main(String[] args) {
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(getSourcePath("source.txt")));
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 550000; i++) {
                String append = new String("fffffffffffffffffffffff");
                builder.append(append);
            }
            outputStream.write(builder.toString().getBytes());
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
