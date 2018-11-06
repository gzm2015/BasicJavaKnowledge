package io;

import java.io.*;

/**
 * @author LiuMengKe
 * @create 2018-11-05-17:07
 */
public class BufferInputStreamTest extends BasicIO {

    public static void main(String[] args) {
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
}
