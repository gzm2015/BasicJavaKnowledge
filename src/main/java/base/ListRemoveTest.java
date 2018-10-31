package base;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author LiuMengKe
 * @create 2018-10-30-16:06
 */
public class ListRemoveTest {

    /**
     * 测试iterator的时候添加删除元素
     * ConcurrentModificationException
     */
    //
    public static void main(String[] args)  {
        add();
        remove();
    }

    private static void remove(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(2);
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            Integer integer = iterator.next();
            if(integer==2)
                list.remove(integer);
        }
    }


    private static void add(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(2);
        Iterator<Integer> iterator = list.iterator();

        while(iterator.hasNext()){
            Integer integer = iterator.next();
            if(integer==2)
                list.add(integer);
        }
    }
}
