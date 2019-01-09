package zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.List;

/**
 * @author gzm2015
 * @create 2018-11-26-13:38
 */
public class BasicZkTest {
    public static  final  String path = "/zoo-book/dogbook";
    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.2.108:2181", new RetryNTimes(10, 5000));
        try {
            client.start();// 连接
            // 获取子节点，顺便监控子节点
            List<String> children = client.getChildren().usingWatcher(new CuratorWatcher() {
                public void process(WatchedEvent event) throws Exception
                {
                    System.out.println("监控： " + event);
                }
            }).forPath("/");
            /*// 创建节点
            String result = client.create().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath("/test", "Data".getBytes());
            System.out.println(result);
            // 设置节点数据
            client.setData().forPath("/test", "111".getBytes());
            client.setData().forPath("/test", "222".getBytes());*/
            final NodeCache nodeCache = new NodeCache(client,path);
            nodeCache.start();
            nodeCache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    System.out.println("监听事件触发");
                    System.out.println("重新获得节点内容为：" + new String(nodeCache.getCurrentData().getData()));
                }
            });

            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 删除节点
            //System.out.println(client.checkExists().forPath("/test"));
            /*client.delete().withVersion(-1).forPath("/test");
            System.out.println(client.checkExists().forPath("/test"));*/
            client.close();
            System.out.println("OK！");
        }
    }

    @Test
    public void test2() {
        try{
            CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.2.108:2181", new RetryNTimes(10, 5000));
            client.start();// 连接
            // 获取子节点，顺便监控子节点
            List<String> children = client.getChildren().usingWatcher(new CuratorWatcher() {
                public void process(WatchedEvent event) throws Exception
                {
                    System.out.println("监控： " + event);
                }
            }).forPath("/");
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/zoo-book/dogbook","init".getBytes());
            Stat stat = new Stat();
            System.out.println(new String(client.getData().storingStatIn(stat).forPath("/zoo-book/dogbook")));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void test() {
        try{
            CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.2.108:2181", new RetryNTimes(10, 5000));
            client.start();
            client.setData().forPath(path,"456".getBytes());
            Thread.sleep(1000);
            client.setData().forPath(path,"789".getBytes());
            Thread.sleep(1000);
            client.setData().forPath(path,"123".getBytes());
            Thread.sleep(1000);
            client.setData().forPath(path,"222".getBytes());
            Thread.sleep(1000);
            client.setData().forPath(path,"333".getBytes());
            Thread.sleep(1000);
            client.setData().forPath(path,"444".getBytes());
        }catch(Exception e){

        }
    }


}
