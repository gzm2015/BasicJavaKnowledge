package io;

import org.junit.Test;

import java.io.*;

/**
 * @author LiuMengKe
 * @create 2018-11-06-10:22
 */
public class BufferStreamTest extends BasicIO {


    @Test
    public void testBufferInput() {
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(getSourcePath("source.txt")));
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testBufferOutput() {
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
