package java8;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuMengKe
 * @create 2018-05-24-20:45
 */
public class MapTest {


    //If the specified key is not already associated with a value (or is mapped to null) associates it with the given value and returns null, else returns the current value.
    //如果以前键有值则返回旧值如果没有返回空
    /*源码解析*/
    /*
    *
    * V v = map.get(key);
         if (v == null)
             v = map.put(key, value);
         return v;
    * */
    @Test
    public void putIfAbsent() {
        Map<String, String> map = new HashMap();
        map.put("key", "value");
        String result = map.putIfAbsent("key", "value2");
        Assert.assertEquals("value",result);

        map.putIfAbsent("key2","val2");
        Assert.assertEquals("val2",map.get("key2"));

    }


    /***旧的键有对应值 就用旧键和旧值构建新的键值关系***/
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
    //present 存在 absent 不在
    @Test
    public void computeIfPresent() {
        Map<String, String> map = new HashMap();
        map.put("key", "value");
        String newValue =  map.computeIfPresent("key", (x, y) -> x + "computeIfPresent有K-V新映射关系时构建" + y);
        Assert.assertEquals("keycomputeIfPresent有K-V新映射关系时构建value",newValue);
        Assert.assertEquals("keycomputeIfPresent有K-V新映射关系时构建value",map.get("key"));
        //消除映射关系
        String newValue2 =  map.computeIfPresent("key", (x, y) -> null);
        Assert.assertNull(newValue2);
        Assert.assertNull(map.get("key"));
        //没有映射关系直接配置funtion失效
        map.computeIfPresent("bbb", (x, y) -> x + "computeIfPresent无K-V新映射关系时构建" + y);
        Assert.assertNull(map.get("bbb"));
    }


    /*旧的键没对应值 使用Function函数构建关系*/
    /*If the specified key is not already associated with a value (or is mapped to null), attempts to compute its value using the given mapping function and enters it into this map unless null.*/
    @Test
    public void computeIfAbsent() {
        Map<String, String> map = new HashMap();
        map.put("key", "key");
        map.computeIfAbsent("key", x -> x + "computeIfAbsent有K-V新映射关系时构建");
        //key
        Assert.assertEquals("key",map.get("key"));
        map.computeIfAbsent("key", x -> null);
        //key
        Assert.assertEquals("key",map.get("key"));
        map.computeIfAbsent("bbb", x -> x + "computeIfAbsent无K-V新映射关系时构建");
        //bbbabsent
        Assert.assertEquals("bbbcomputeIfAbsent无K-V新映射关系时构建",map.get("bbb"));
    }


    /*键值对匹配才能移除*/
    //源码
        /*if (map.containsKey(key) && Objects.equals(map.get(key), value)) {
            map.remove(key);
            return true;
        } else
            return false;*/
    @Test
    public void testRemove() {
        Map<String, String> map = new HashMap();
        map.put("key", "key");
        map.remove("key", "bbb");
        //key
        Assert.assertNotNull(map.get("key"));
        map.remove("key", "key");
        //null
        Assert.assertNull(map.get("key"));
    }


    //用key获取没有对应值返回使用默认值key不存在返回默认值
    /*
     * Returns the value to which the specified key is mapped, or
     * {@code defaultValue} if this map contains no mapping for the key.
     * 取key对应值 如果map中key没有对应值则返回默认值
     */
    @Test
    public void getOrDefault() {
        Map<String, String> map = new HashMap();
        map.put("key",null);
        String result = map.getOrDefault("key", "ccc");
        String result2 = map.getOrDefault("key2", "ccc");
        Assert.assertEquals(result,null);
        Assert.assertEquals(result2,"ccc");
    }

    /*没有映射使用当前的值 有映射则传入旧值 和 新值 做 操作 作为新的 值 如果为null 移除映射关系*/

    /*  V oldValue = map.get(key);
        V newValue = (oldValue == null) ? value :
          remappingFunction.apply(oldValue, value);
        if (newValue == null)
            map.remove(key);
        else
            map.put(key, newValue);*/
    @Test
    public void merge() {
        Map<String, String> map = new HashMap();

        map.merge("key", "bbb", (x, y) -> x + y);
        //bbb
        Assert.assertEquals(map.get("key"),"bbb");

        map.merge("key", "ccc", (x, y) -> x + y);
        //bbbccc
        Assert.assertEquals(map.get("key"),"bbbccc");

        map.merge("key", "ccc", (x, y) -> null);
        //null
        Assert.assertEquals(map.get("key"),null);

    }
}
