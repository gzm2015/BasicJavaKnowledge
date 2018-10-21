package java8;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuMengKe
 * @create 2018-05-24-20:45
 */
public class Jdk8MapTest {

    @Test
    public void testputIfAbsent() {

        //If the specified key is not already associated with a value (or is mapped to null) associates it with the given value and returns null, else returns the current value.
        //如果以前键有值 则 返回 旧值 如果没有返回空

        /*源码解析*/
        /*
        *
        * V v = map.get(key);
             if (v == null)
                 v = map.put(key, value);
             return v;
        * */
        Map<String, String> map = new HashMap();
        map.put("aaa", "aaa");

        String result = map.putIfAbsent("aaa", "bbb");

        //aaa
        System.out.println(result);

        //null
        System.out.println(map.putIfAbsent("bbb", "bbb"));
    }


    /***旧的键有对应值 就用旧键和旧值构建新的键值关系***/
    @Test
    public void testcomputeIfPresent() {
        Map<String, String> map = new HashMap();
        map.put("aaa", "aaa");

        //computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)
        //x 为 旧的映射键 y 为旧映射的值
        //先取老的值 再根据BiFunction计算传入老的键和值计算新值 重新构建映射关系 如果没有则老的映射为null
        /*if (map.get(key) != null) {
            V oldValue = map.get(key);
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null)
                map.put(key, newValue);
            else
                map.remove(key);
        }*/


        map.computeIfPresent("aaa", (x, y) -> x + "present" + y);
        //aaapresentaaa
        System.out.println(map.get("aaa"));

        map.computeIfPresent("aaa", (x, y) -> null);

        String result = map.get("aaa");
        //null
        System.out.println(result);

        map.computeIfPresent("bbb", (x, y) -> x + "present" + y);
        //null
        System.out.println(map.get("bbb"));
    }


    /*旧的键没对应值 使用Function函数构建关系*/
    /*If the specified key is not already associated with a value (or is mapped to null), attempts to compute its value using the given mapping function and enters it into this map unless null.*/
    @Test
    public void testcomputeIfAbsent() {
        Map<String, String> map = new HashMap();
        map.put("aaa", "aaa");

        map.computeIfAbsent("aaa", x -> x + "absent");

        //aaa
        System.out.println(map.get("aaa"));
        map.computeIfAbsent("aaa", x -> null);

        //aaa
        System.out.println(map.get("aaa"));

        map.computeIfAbsent("bbb", x -> x + "absent");

        //bbbabsent
        System.out.println(map.get("bbb"));

    }


    /*键值对匹配才能移除*/
    @Test
    public void testRemove() {
        Map<String, String> map = new HashMap();
        map.put("aaa", "aaa");

        //源码
        /*if (map.containsKey(key) && Objects.equals(map.get(key), value)) {
            map.remove(key);
            return true;
        } else
            return false;*/

        map.remove("aaa", "bbb");
        //aaa
        System.out.println(map.get("aaa"));

        map.remove("aaa", "aaa");
        //null
        System.out.println(map.get("aaa"));
    }


    @Test
    public void getOrDefault() {
        //使用默认值
        Map<String, String> map = new HashMap();
        map.getOrDefault("aaa", "ccc");
        System.out.println(map.get("aaa"));
    }

    @Test
    public void merge() {
        Map<String, String> map = new HashMap();
        /*没有映射使用当前的值 有映射 则传入 旧值 和 新值 做 操作 作为新的 值 如果为null 移除映射关系*/

        /*  V oldValue = map.get(key);
            V newValue = (oldValue == null) ? value :
              remappingFunction.apply(oldValue, value);
            if (newValue == null)
                map.remove(key);
            else
                map.put(key, newValue);*/

        map.merge("aaa", "bbb", (x, y) -> x + y);
        //bbb
        System.out.println(map.get("aaa"));

        map.merge("aaa", "ccc", (x, y) -> x + y);
        //bbbccc
        System.out.println(map.get("aaa"));

        map.merge("aaa", "ccc", (x, y) -> null);
        //null
        System.out.println(map.get("aaa"));

    }
}
