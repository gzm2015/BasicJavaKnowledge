package java8;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author LiuMengKe
 * @create 2018-05-25-13:52
 */
@Data
@AllArgsConstructor
public class Son {

    private String name;

    @Override
    public String toString() {
        return "Son{" +
                "name='" + name + '\'' +
                '}';
    }
}
