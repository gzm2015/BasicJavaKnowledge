package java8;

import java.util.Objects;

/**
 * @author LiuMengKe
 * @create 2018-05-25-10:07
 */
public class Person {

    private String name;

    private Son son;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Son getSun() {
        return son;
    }

    public void setSun(Son son) {
        this.son = son;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
