package java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author LiuMengKe
 * @create 2018-05-25-9:18
 */
public class StreamAndOptionalTest {

    List<String> myList =
            Arrays.asList("a1", "a2", "b1", "c2", "c1");


    @Test
    public void testStream() {

        myList.stream().filter(x -> {
            if (x.startsWith("c")) {
                return true;
            } else {
                return false;
            }
        }).map(x -> x.toUpperCase()).forEach(x -> System.out.println(x));

        System.out.println("=======================================");

        myList.stream().filter(x -> x.startsWith("c")).map(String::toUpperCase).forEach(System.out::println);

    }

    @Test
    public void testOptional() {
        //ifPresent If a value is present, invoke the specified consumer with the value, otherwise do nothing.
        //A1
        myList.stream().findFirst().map(String::toUpperCase).ifPresent(System.out::println);

        //isPresent Return true if there is a value present, otherwise false.
        boolean present = myList.stream().findFirst().map(String::toUpperCase).isPresent();
        //true
        System.out.println(present);

        //Optional.of Returns an Optional with the specified present non-null value.
        Optional optional = Optional.of("aaaa");
        //aaa
        System.out.println(optional.orElse("bbbb"));

        //Optional.ofNullable Returns an Optional describing the specified value, if non-null, otherwise returns an empty Optional.
        Optional optional2 = Optional.ofNullable(null);
        //orElse Return the value if present, otherwise return other.
        System.out.println(optional2.orElse("bbbb"));
        System.out.println(optional2.orElseGet(
                () -> {
                    return "ffffffff";
                }
        ));

    }


    /*直接用 对象作为steam 的成员 和 基本数据类型的stream */
    @Test
    public void testTypeStream() {

        //直接用对象作为stream
        Stream<Person> stream = Stream.of(new Person("郭源潮"), new Person("哈哈哈哈"), new Person("郭源潮"));

        //distince 必须复写 equal 和 hascode方法
        stream.distinct().filter(person -> person.getName().endsWith("郭源潮")).forEach(System.out::println);


        //IntStream
        IntStream intStream1 = IntStream.of(1, 2, 3, 4, 5);
        IntStream intStream2 = IntStream.range(6, 11);
        intStream1.forEach(System.out::print);

        System.out.println();
        //public static IntStream stream(int[] array)
        //149
        Arrays.stream(new int[]{1, 2, 3}).map(x -> x * x).forEach(System.out::print);

        System.out.println();

        //ifPresent后关闭stream
        intStream2.average().ifPresent(System.out::print);

        System.out.println();
        //使用supplier 接口每次指向一个新的IntStream
        Supplier<IntStream> streamSupplier = () -> IntStream.of(1, 2, 3, 4, 5);

        //5
        long count = streamSupplier.get().count();
        System.out.println(count);

        //5
        streamSupplier.get().max().ifPresent(System.out::print);
    }


    @Test
    public void testCollect() {

        Supplier<Stream<Person>> streamSupplier = () -> Stream.of(new Person("郭源潮"), new Person("哈哈哈哈"), new Person("郭源潮"));
        Stream<Person> stream = Stream.of(new Person("郭源潮"), new Person("哈哈哈哈"), new Person("郭源潮"));

        List<Person> personList = streamSupplier.get().filter(x -> x.getName().startsWith("郭")).collect(Collectors.toList());
        //[Person{name='郭源潮'}, Person{name='郭源潮'}]
        System.out.println(personList);

        //groupby 的条件将会作为map的key
        Map map = streamSupplier.get().collect(Collectors.groupingBy(p -> p.getName()));

        //{哈哈哈哈=[Person{name='哈哈哈哈'}], 郭源潮=[Person{name='郭源潮'}, Person{name='郭源潮'}]}
        System.out.println(map);

        String result = streamSupplier.get().map(person -> person.getName()).collect(Collectors.joining("|", "AAAA", "BBBBB"));
        String result2 = streamSupplier.get().map(person -> person.getName()).collect(Collectors.joining("|"));
        System.out.println(result);
        System.out.println(result2);


        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> {
                            System.out.println("supplier========");
                            return new StringJoiner(" | ");
                        },          // supplier
                        (j, p) -> {
                            System.out.println("accumulator==========");
                            System.out.println(j);
                            System.out.println(p);
                            j.add(p.getName().toUpperCase());
                        },  // accumulator
                        (j1, j2) -> {
                            System.out.println("combiner==============");
                            System.out.println(j1);
                            System.out.println(j2);
                            return j1.merge(j2);
                        },// combiner
                        stringJoiner -> {
                            System.out.println("finish==========");
                            return stringJoiner.toString();
                        });                // finisher

        String names = streamSupplier.get()
                .collect(personNameCollector);
        System.out.println(names);


        //flatmap <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
        //map <R> Stream<R> map(Function<? super T, ? extends R> mapper);


    }


    @Test
    public void testStreamFlapMap() {
        //flatmap <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
        //map <R> Stream<R> map(Function<? super T, ? extends R> mapper);
        Person p1 = new Person("郭源潮");
        Person p2 = new Person("哈哈哈哈");
        Person p3 = new Person("郭源潮2");
        Son sun3 = new Son("sun");
        p3.setSun(sun3);
        Supplier<Stream<Person>> streamSupplier = () -> Stream.of(p1, p2, p3);
        streamSupplier.get().map(x -> Stream.of(x.getSun())).forEach(x -> x.forEach(y -> System.out.println(y)));

        System.out.println("============================");

        streamSupplier.get().flatMap(person -> Stream.of(person.getSun())).forEach(System.out::println);


        streamSupplier.get().flatMap(person -> Stream.of(person.getSun())).forEach(System.out::println);

    }


    @Test
    public void testForkJoin() {
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));

    }


    @Test
    public void testSort() {

        //sort看起来只在主线程上串行执行。实际上，并行流上的sort在背后使用了Java8中新的方法Arrays.parallelSort()
        //这个方法会参照数据长度来决定以串行或并行来执行。
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort: %s <> %s [%s]\n",
                            s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));

    }

    /***********观察combiner 中的执行情况    ************/
    @Test
    public void testBingXing() {
        List<Human> persons = Arrays.asList(
                new Human("Max", 18),
                new Human("Peter", 23),
                new Human("Pamela", 23),
                new Human("David", 12));

        persons
                .parallelStream()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s [%s]\n",
                                    sum, p, Thread.currentThread().getName());
                            return sum += p.getAge();
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
                                    sum1, sum2, Thread.currentThread().getName());
                            return sum1 + sum2;
                        });

        System.out.println("==============================================");

        persons
                .stream()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s [%s]\n",
                                    sum, p, Thread.currentThread().getName());
                            return sum += p.getAge();
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
                                    sum1, sum2, Thread.currentThread().getName());
                            return sum1 + sum2;
                        });

    }


    public class Human {

        private String name;
        private int age;

        public Human(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Human{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

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
}


