package base;

import io.BasicIO;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author LiuMengKe
 * @create 2018-12-29-16:11
 */
public class ExceptionTest {

    /*Java 异常处理的基本原则就是 必须为每一个受查异常 提供对应的异常处理器*/
    /**
     * Throwable
     *      --- Exception
     *              ----RuntimeException 程序错误导致的 类型转换 数组越界 空指针
     *              ----IOException 程序没问题而是像I/O这类问题导致其他异常
     *      --- Error
     * unchecked  Error RunttimeException
     * checked  编译器将坚持是否为所有受查异常提供了异常处理器
     * 根据异常规范发生下面四种情况的时候需要抛出异常 其中 1.2受查异常的处理 34是非受查异常的处理
                  1.当调用一个抛出受查异常的方法
                  2.程序运行发现错误并用throw抛出一个受查异常的
                  3.程序错误出现ArrayIndexOutOfBoundsException这种非受查异常
                  4.java虚拟机出现错误
     *  子类不能比抛出的异常只能extends父类的异常
     */

    
    /**
     * JavaSe7 try中带资源的可以在try后面直接写资源以此避免还需要在finally中继续关闭资源
     */
    @Test
    public void SourceTryTest() {
        //AutoCloseable
        try (FileReader fileReader = new FileReader(BasicIO.getSourcePath("source.txt"))){
            char[] chars = new char[1024];
            int len = 0;
            while ((len = fileReader.read(chars))!=-1){
                System.out.println(new String(chars,0,len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void catchTest() {
        try{
            System.out.println("开始测试异常捕获");
            int a = 1/0;
            assert a>0;//直接在程序中使用断言是开发和调试中的手段不要直接在生产环境使用
            throw new MyException();
        }catch(MyException e){
            System.out.println("自定义异常被捕获");
        }
    }


    public class MyException extends Exception{

    }

    public class SonException extends MyException{

    }


}
