package collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

/**
 * 集合特性测试
 *
 * @author gzm2015
 * @create 2019-01-08-15:43
 */
public class CollectionTest {


    /**
     * 1.集合的基础是Collection<T> extends Iterable
     * Iterator<T> iterator();
     * 因此每个集合子类都能用for each 循环遍历 或者使用  stream().foreach
     * 也因此me米格子类可以有各自Iterator实现，比如set的iterator就没有add方法而linkedlist iterator就可以add 元素
     */
    @Test
    public void basicTest() {
        //Arrays.asList 返回的是内部类中的ArrayList它本质上是一个视图对象含有访问里面数组的getset方法，
        // 但是所有改变底层数组大小的方法（iterator add) 会报错 Unsupported Operation
        //private final E[] a; 因为数组在内部类中final修饰是不可变的
        // ArrayList iterator.add的时候会出错
        List<String> list1 = new LinkedList<>();
        list1.add("A");
        list1.add("B");
        list1.add("C");
        ListIterator<String> iterator = list1.listIterator();
        //iterator.next方法返回的是上一个元素
        String v1 = iterator.next();
        Assert.assertEquals("A", v1);
        //链表越过A以后插入一个元素
        iterator.add("A1");
        //插入元素后再次next返回的是未插入前的下一个元素的值
        String v2 = iterator.next();
        Assert.assertEquals("B", v2);

        //因为使用的是从头到尾变量删除的是上一个获得的元素B相当于backspace
        //如果使用previous从尾部到头遍历则删除的也是上一个元素相当于del
        iterator.remove();
        Assert.assertEquals(3, list1.size());


        String v3 = iterator.next();
        Assert.assertEquals("C", v3);

        //设置C变成D
        iterator.set("D");
        System.out.println("");
    }

    @Test
    public void listIteratorTest() {

        List<String> list1 = new LinkedList<>();
        list1.add("A1");
        list1.add("B1");
        list1.add("C1");

        List<String> list2 = new LinkedList<>();
        list2.add("A2");
        list2.add("B2");
        list2.add("C2");
        list2.add("D2");
        list2.add("E2");

        merge(list1, list2);
        remove(list2);

        Assert.assertEquals(3, list2.size());
    }


    //将list2的内容间隔的添加到list1中
    private void merge(List<String> list1, List<String> list2) {
        Iterator<String> iteB = list2.iterator();
        ListIterator<String> iteA = list1.listIterator();
        while (iteB.hasNext()) {
            String loop = iteB.next();
            if (iteA.hasNext()) {
                iteA.next();
                iteA.add(loop);
            } else {
                //注意这里不要 list1.add()
                iteA.add(loop);
            }
        }
    }

    //隔列删除
    private void remove(List list2) {
        Iterator<String> iteB = list2.iterator();
        while (iteB.hasNext()) {
            iteB.next();
            if (iteB.hasNext()) {
                iteB.next();
                iteB.remove();
            }
        }
    }


    @Test
    public void hashTest() {
        //String 覆写了 equal 直接比较字符串 字符串内容相等hashcode相等
        Assert.assertEquals("Hash".hashCode(), "Hash".hashCode());
        Assert.assertEquals("Hash", new String("Hash"));
        Assert.assertEquals("Hash".hashCode(), new String("Hash").hashCode());

        //计算元素在bucket 中的位置
        //比如有128个桶取余决定了位置 位置被占散列冲突进去桶内链表 进行equal比较
        //java8以后将链表替换为平衡二叉树
        System.out.println(76268 % 128);
    }


    @Test
    public void treeSetTest() {
        /*TreeSet<String> treeSet = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });*/
        TreeSet<Item> treeSet = new TreeSet<>(Item::compareTo);
    }


    //利用merge对map操作
    //对map中的值进行递增操作
    @Test
    public void mapTest() {
        /**
         * 传统方式都要进行判空操作 然后每次得到的再继续进行加一操作
         */
        Map<String, Integer> map = new HashMap<>();
        map.merge("work", 1, Integer::sum);
        Assert.assertEquals(Integer.valueOf(1), map.get("work"));
        map.merge("work", 1, Integer::sum);

        map.put("key2", 2);
        //keySet 返回一个实现了Set接口对象的类对象 通过他对原映射进行操作。这种集合称为视图
        for (String key : map.keySet()) {
            map.get(key);
        }

        //Set<Map.Entry<String,Integer>> keySet = map.entrySet();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    //枚举集测试
    @Test
    public void enumTest() {

        EnumSet<WeekDay> always = EnumSet.allOf(WeekDay.class);
        EnumSet<WeekDay> never = EnumSet.noneOf(WeekDay.class);
        EnumSet<WeekDay> workday = EnumSet.range(WeekDay.MONDAY, WeekDay.FRIDAY);
        EnumSet<WeekDay> mwf = EnumSet.of(WeekDay.MONDAY, WeekDay.FRIDAY);

    }


    //视图特性测试
    @Test
    public void viewTest() {
        //Arrays.asList 返回的是内部类中的ArrayList它本质上是一个视图对象含有访问里面数组的getset方法，
        // 但是所有改变底层数组大小的方法（iterator add) 会报错 Unsupported Operation
        //private final E[] a; 因为数组在内部类中final修饰是不可变的
        // ArrayList iterator.add的时候会出错
        String[] array = {"A", "B", "C"};
        List<String> list = Arrays.asList(array);
        ListIterator<String> iterator = list.listIterator();
        iterator.next();
        try {
            iterator.add("A1");
        } catch (Exception e) {
            Assert.assertEquals(UnsupportedOperationException.class, e.getClass());
        }

        //返回包含100个字符串的list每个都是default
        List<String> list2 = Collections.nCopies(100, "default");
        //返回不可变单元素集
        Set<String> singleSet = Collections.singleton("default");

        List<String> list3 = new ArrayList<>(list2);
        List<String> list2Sub = list3.subList(0, 10);
        //不可对list2进行clear 原因同上
        //对子视图清空的同时 原集合收到影响
        list2Sub.clear();
        Assert.assertEquals(90, list3.size());

        //为泛型泛型提混入其他类型提供调试支持
        List<String> safeStrings = Collections.checkedList(list3, String.class);
        //多线程支持
        Map<String, Item> map = Collections.synchronizedMap(new HashMap<String, Item>());
        //不能修改的集合
        List<String> unmodifyList = Collections.unmodifiableList(list3);
        try {
            unmodifyList.set(3, "A");
        } catch (Exception e) {
            Assert.assertEquals(UnsupportedOperationException.class, e.getClass());
        }
    }


    @Test
    public void collectionTest() {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        //混淆list中元素顺序
        Collections.shuffle(list);
        List<Integer> sub = list.subList(0, 10);
        Collections.sort(sub);
        System.out.println(sub);
    }


    /**
     * 集合数组转换
     */
    @Test
    public void conVertTest() {
        String[] arrays = new String[10];
        List<String> stringList = Arrays.asList(arrays);
        String[] converted = (String[])stringList.toArray();
    }

    /**
     * 优先级队列测试
     * 采用了堆（可以自我调整的二叉树） add remove 操作会让最小元素移动到根
     */
    @Test
    public void priorityQueueTest() {
        PriorityQueue<LocalDate> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(LocalDate.of(1999,12,1));
        priorityQueue.add(LocalDate.of(1996,1,1));
        priorityQueue.add(LocalDate.of(1993,8,1));
        priorityQueue.add(LocalDate.of(1998,9,1));
        priorityQueue.add(LocalDate.of(1990,12,1));

        for (LocalDate localDate : priorityQueue) {
            System.out.println(localDate);
        }
        System.out.println("========================");
        while (!priorityQueue.isEmpty()){
            System.out.println(priorityQueue.remove());
        }
    }


    public enum WeekDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Item implements Comparable<Item> {
        private String name;

        @Override
        public int compareTo(Item o) {
            return this.getName().compareTo(o.getName());
        }
    }

}
