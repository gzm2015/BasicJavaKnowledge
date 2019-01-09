package generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gzm2015
 * @create 2019-01-07-10:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pair<T> {

    private T first;
    private T second;


    /*类型变量放在方法中 要放在返回值前面 才能在参数中进行引用*/
    /**
     *
     */
    public <T> void  print(T t){
        System.out.println(this.getClass()+"    "+t);
    }

    //排序并返回排序后的数组
    //注意入参必须是可以排序的参数所以类型参数必须extendsComparable
    //另外泛型里面的extends 是一个抽象的概念可以理解为 extends类或implements方法多个之间可以用，分隔 可以用&间隔条件
    public <T extends Comparable,Object> T[] compare(T[] arrays){
        return null;
    }



}
