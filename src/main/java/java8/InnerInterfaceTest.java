package java8;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author LiuMengKe
 * @create 2018-12-25-16:33
 *
 * java8 内置函数接口测试
 */
public class InnerInterfaceTest {

    /*Predicate是一个布尔类型的函数，该函数只有一个输入参数。Predicate接口包含了多种默认方法，用于处理复杂的逻辑动词（and, or，negate）*/
    @Test
    public void predicate () {
        Predicate<String> predicate = (s) -> s.length() > 0;

        predicate.test("foo");              // true
        predicate.negate().test("foo");     // false  对预测结果取反

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
    }

    //Function接口接收一个参数，并返回单一的结果。默认方法可以将多个函数串在一起（compse, andThen）
    @Test
    public void function() {
        //String.valueof(Integer.valueof("123"))
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        backToString.apply("123");     // "123"
    }


    //Supplier接口产生一个给定类型的结果。与Function不同的是，Supplier没有输入参数。
    @Test
    public void suppliers() {
        Supplier<Person> personSupplier = Person::new;
        personSupplier.get();   // new Person
    }

    @Test
    public void customer() {
        Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.name);
        greeter.accept(new Person("Luke", "Skywalker"));
    }


    @Test
    public void compare() {

        Comparator<Person> comparator = (p1, p2) -> p1.name.compareTo(p2.name);

        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Wonderland");

        comparator.compare(p1, p2);             // > 0
        comparator.reversed().compare(p1, p2);  // < 0

    }


    @NoArgsConstructor
    @AllArgsConstructor
    public class Person{
        private String name;
        private String nickName;
    }
}
