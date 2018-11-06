package io;

import java.io.*;

/**
 * @author LiuMengKe
 * @create 2018-11-05-17:20
 * 对比字节流和buffer效率
 */
public class ByteBufferedCompareTest extends BasicIO{



    public static void byteCopy(String src,String target) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(src);
            outputStream = new FileOutputStream(target);
            int len= 0;
            byte[] bytes = new byte[BUF_SIZE];
            while ((len=inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                outputStream.close();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
    }

    public static void bufferCopy(String src,String target) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(src));
            outputStream = new BufferedOutputStream(new FileOutputStream(target));
            int len= 0;
            byte[] bytes = new byte[BUF_SIZE];
            while ((len=inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                outputStream.close();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
    }

    /**
     * 1541465852049
     * 1541465852163
     * 114
     * 1541465852163
     * 1541465852207
     * 44
     * Buffer比Byte方式快很多
     */
    public static void main(String[] args) {
        try {
            long method1begin = System.currentTimeMillis();
            System.out.println(method1begin);
            byteCopy(getSourcePath("source.txt"),getSourcePath("target.txt"));
            long method1end = System.currentTimeMillis();
            System.out.println(method1end);
            System.out.println(method1end-method1begin);
            long method2begin = System.currentTimeMillis();
            System.out.println(method2begin);
            bufferCopy(getSourcePath("source.txt"),getSourcePath("target.txt"));
            long method2end = System.currentTimeMillis();
            System.out.println(method2end);
            System.out.println(method2end-method2begin);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    


}
