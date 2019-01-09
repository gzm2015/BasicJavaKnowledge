package collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 工具类api
 *
 * @author LiuMengKe
 * @create 2019-01-09-14:25
 */
public class CollectionsTest {

    /**
     * 对于随机访问的可以使用二分查找
     */
    @Test
    public void binarySearchTest() {

        CollectionTest.Item item = new CollectionTest().new Item("A");
        CollectionTest.Item item2 = new CollectionTest().new Item("B");
        CollectionTest.Item item3 = new CollectionTest().new Item("C");
        List<CollectionTest.Item> list = getItemList();
        list.add(item);
        list.add(item3);
        list.add(item2);
        int result = Collections.binarySearch(list,item2,CollectionTest.Item::compareTo);

        //result 表示查找到元素在有序列表中索引 大于等于0 表示找到
        //由于此时的list未排序 所有找不到的
        Assert.assertTrue(result<0);

        //排序后重新查找
        Collections.sort(list,CollectionTest.Item::compareTo);
        result = Collections.binarySearch(list,item2,CollectionTest.Item::compareTo);
        Assert.assertTrue(result>=0);


    }

    /**
     * 值得留意的工具类apI
     * max min
     * fill 替换为相同的值 copy
     * addAll replaceAll removeIf
     * swap 指定位置交换 reverse 列表反转
     * frequency 含有两个相同元素的个数 disjoint 没有共同元素而返回true
     */
    @Test
    public void apiTest() {

        CollectionTest.Item max =   Collections.max(getItemList(),CollectionTest.Item::compareTo);
        Assert.assertEquals("C",max.getName());

        //fill 全部替换为相同的值
        List<CollectionTest.Item> fill = getItemList();
        Collections.fill(fill,max);
        List<CollectionTest.Item> filteredFillList = fill.stream().filter((x)->x.getName().equals("C")).collect(Collectors.toList());
        Assert.assertTrue(filteredFillList.size()==3);

        //原生jdk Collections.copy 浅拷贝 会报错 IndexOutOfBoundsException("Source does not fit in dest");
        //原因是if (srcSize > dest.size()) 此时copyDest 还未分配元素
        List<CollectionTest.Item> copySrc = getItemList();
        List<CollectionTest.Item> copyDestBad = new ArrayList<>();
        try{
            Collections.copy(copyDestBad,copySrc);
        }catch(Exception e){
            Assert.assertEquals(IndexOutOfBoundsException.class,e.getClass());
            Assert.assertEquals("Source does not fit in dest",e.getMessage());
        }

        List<CollectionTest.Item> copyDestGood = Arrays.asList(new CollectionTest.Item[copySrc.size()]);
        Collections.copy(copyDestGood,copySrc);
        //浅拷贝 列表中引用的是同一批对象
        copySrc.forEach(x->System.out.println(x.hashCode()));
        copyDestGood.forEach(x->System.out.println(x.hashCode()));

        List<CollectionTest.Item> reverse = getItemList();
        Collections.reverse(reverse);
        Assert.assertEquals("A",reverse.get(2).getName());

        List<CollectionTest.Item> swap = getItemList();
        Collections.swap(swap,1,2);
        Assert.assertEquals("C",swap.get(2).getName());

        List<CollectionTest.Item> remove = getItemList();
        remove.removeIf(x->"A".equals(x.getName()));
        Assert.assertEquals(2,remove.size());
        //map对每个元素有操作有返回值也不是流的终止操作
        //remove.stream().filter(x->"B".equals(x.getName())).map(x->remove.remove(x));
        //下面操作会报 ConcurrentModificationException
        try{
            remove.stream().filter(x->"B".equals(x.getName())).forEach(x->remove.remove(x));
        }catch(Exception e){
            Assert.assertEquals(ConcurrentModificationException.class,e.getClass());
        }
        //正确方法是使用removeIf
        remove.removeIf(x->"B".equals(x.getName()));
        Assert.assertEquals(1,remove.size());


    }

    /**
     * 利用批量操作求 交集并集补集
     * 交集 同时属于 A B
     * 并集 A B 中所有元素
     * 补集 属于A不属于B 或相反
     * removeAll c1.removeAll(c2) 从c1中移除所有c2的元素  用来求补集
     * retainAll c1.retainAll(c2) 从c1中移除未所有c2的元素 可以用来求交集
     * 求并集 如果是Set直接合并就行
     */
    @Test
    public void batchCollectionTest() {

        List<String> srcListA = Arrays.asList("A","B","C");
        List<String> srcListB = Arrays.asList("D","B","C");

        //求AB交集
        List<String> acopy = new ArrayList<>(srcListA);
        Assert.assertTrue(acopy.retainAll(srcListB));
        Assert.assertEquals(2,acopy.size());

        //求A对B的补集
        List<String> acopy2 = new ArrayList<>(srcListA);
        Assert.assertTrue(acopy2.removeAll(srcListB));
        Assert.assertEquals(1,acopy2.size());

        //求AB并集 A,B先求补集再合并
        List<String> acopyB = new ArrayList<>(srcListB);
        acopyB.addAll(acopy2);
        Assert.assertEquals(4,acopyB.size());
    }

    public List<CollectionTest.Item> getItemList(){
        CollectionTest.Item item = new CollectionTest().new Item("A");
        CollectionTest.Item item2 = new CollectionTest().new Item("B");
        CollectionTest.Item item3 = new CollectionTest().new Item("C");
        List<CollectionTest.Item> list = new ArrayList<>();
        list.add(item);
        list.add(item3);
        list.add(item2);
        return list;
    }
}
