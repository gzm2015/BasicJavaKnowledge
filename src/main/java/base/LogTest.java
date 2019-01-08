package base;

import org.junit.Test;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author LiuMengKe
 * @create 2019-01-07-10:13
 * 用system输出会到处都是也不好控制
 * 使用log的优点有
 *      1.容易取消某个类别的日志 打开关闭都很容易
 *      2.日志记录器可以定向到多个日志处理器 控制日志是控制台显示还是存储
 *      3.日志记录器和处理器可以对日志进行过滤
 */
public class LogTest {

    private Logger myLogger = Logger.getLogger("base");

    //简单测试一下全局日志记录
    @Test
    public void LogTest() {
        Logger.getGlobal().info("test log ");
    }

    //企业级别日志记录
    @Test
    public void EnterpriseTest() {
        //默认情况只处理 Level.INFO Level.SEVERE Level.WARNING 这三种级别
        myLogger.setLevel(Level.INFO);
        myLogger.setLevel(Level.SEVERE);
        myLogger.setLevel(Level.WARNING);

        //打开日志记录 关闭日志记录 Level.ALL;Level.OFF
        //下面这几个方法是不论什么级别都有的
        myLogger.info("log info");
        myLogger.warning("log warning");
        myLogger.fine("log warning");
    }


    /**
     * 默认情况下logger 会将记录发送到自己的处理器和父处理器最终的处理器是一个名为”“的处理器
     * 处理器再将流输出到System.out中
     * 下面测试将会打印两次信息 就是两个处理器分别输出了一次
     */
    @Test
    public void EnterpriseTest2() {
        myLogger.setLevel(Level.FINE);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        myLogger.addHandler(handler);
        myLogger.info("message");
        // 可以通过下面设置关闭默认父处理器“”
        // myLogger.setUseParentHandlers(false);

        //另外还有 FileHandler SocketHandler 等输出到文件 和 服务器指定端口的用法
    }


}
