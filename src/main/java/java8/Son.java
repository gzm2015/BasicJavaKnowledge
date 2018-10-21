package java8;

/**
 * @author LiuMengKe
 * @create 2018-05-25-13:52
 */
public class Son {

    private String name;

    public Son() {
    }

    public Son(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Son{" +
                "name='" + name + '\'' +
                '}';
    }
}
