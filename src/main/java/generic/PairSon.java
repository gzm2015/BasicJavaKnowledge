package generic;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author LiuMengKe
 * @create 2019-01-07-10:59
 */
@Data
public class PairSon<T,U> extends Pair<T>{

    private U u;

    public PairSon(T first, U u) {
        super(first,first);
        this.u = u;
    }

    //由于父类中采用了泛型进行类型擦除以后 相当于public  void  print(Object param)
    //所以编译器会自动对从父类继承的方法生成一个桥方法 相当于
    /*
    *  public void print(Object param){
    *       print((String) param))
    *  }
    *
    *
    * */
    public  void  print(String param){
        System.out.println(this.getClass()+"    "+param);
    }

}
