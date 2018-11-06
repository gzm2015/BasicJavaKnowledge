package io;

import org.junit.Test;
import java.io.File;
import java.io.IOException;

/**
 * @author LiuMengKe
 * @create 2018-11-05-16:10
 * https://www.cnblogs.com/baixl/p/4170599.html
 */
public class BasicIO {
    
    /**
     * 字节流-缓冲流（重点）
     * 输入流InputStream-FileInputStream-BufferedInputStream
     * 输出流OutputStream-FileOutputStream-BufferedOutputStream
     * 字符流-缓冲流（重点）
     * 输入流Reader-FileReader-BufferedReader
     * 输出流Writer-FileWriter-BufferedWriter
     * 转换流
     * InputSteamReader和OutputStreamWriter
     * 对象流ObjectInputStream和ObjectOutputStream（难点）
     * 序列化
     * 反序列化
     * 随机存取流RandomAccessFile（掌握读取、写入）
     */
    static int BUF_SIZE = 1024;

    public static String getSourcePath(String fileName) throws IOException {
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        return courseFile+"\\src\\main\\resources\\"+fileName;
    }

    /**
     * java 获取路径测试
     * 测试结果
     * F:\lmkGithubDemo\BasicJavaKnowledge
     * file:/F:/lmkGithubDemo/BasicJavaKnowledge/target/classes/io/source.txt
     * file:/F:/lmkGithubDemo/BasicJavaKnowledge/target/classes/source.txt
     * /F:/lmkGithubDemo/BasicJavaKnowledge/target/classes/io/source.txt
     * /F:/lmkGithubDemo/BasicJavaKnowledge/target/classes/source.txt
     */
    @Test
    public void filePathTest() {
        File directory = new File("");// 参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
            System.out.println(courseFile);
            System.out.println(BasicIO.class.getResource("")+"source.txt");
            System.out.println(BasicIO.class.getResource("/")+"source.txt");
            System.out.println(BasicIO.class.getResource("").getPath()+"source.txt");
            System.out.println(BasicIO.class.getResource("/").getPath()+"source.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
