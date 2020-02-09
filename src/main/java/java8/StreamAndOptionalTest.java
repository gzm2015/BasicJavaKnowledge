package java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author gzm2015
 * @create 2018-05-25-9:18
 */
public class StreamAndOptionalTest {

    List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");

    /**
     * 数据流操作要么是衔接操作，要么是终止操作。
     * 衔接操作返回数据流，所以我们可以把多个衔接操作不使用分号来链接到一起。
     * 终止操作无返回值，或者返回一个不是流的结果。在上面的例子中，filter、map和sorted都是衔接操作，而forEach是终止操作。
     * <p>
     * 数据流可以从多种数据源创建，尤其是集合。List和Set支持新方法stream() 和 parallelStream()，
     */
    @Test
    public void stream() {

        myList.stream().filter(x -> {
            if (x.startsWith("c")) {
                return true;
            } else {
                return false;
            }
        }).map(String::toUpperCase).forEach(System.out::println);

        System.out.println("=======================================");

        //并发
        myList.parallelStream().filter(x -> x.startsWith("c")).map(String::toUpperCase).forEach(System.out::println);

    }


    /*直接用 对象作为steam 的成员 和 基本数据类型的stream */
    @Test
    public void testTypeStream1() {
        //直接用对象作为stream
        Stream<Person> stream = Stream.of(new Person("郭源潮"), new Person("哈哈哈哈"), new Person("郭源潮"));
        //distince 必须复写 equal 和 hascode方法
        stream.distinct().filter(person -> person.getName().endsWith("郭源潮")).forEach(System.out::println);
    }

    @Test
    public void testTypeStream2() {
        //IntStream
        IntStream intStream1 = IntStream.of(1, 2, 3, 4, 5);
        IntStream intStream2 = IntStream.range(6, 11);
        intStream1.forEach(System.out::print);
        System.out.println();
        //ifPresent后关闭stream
        intStream2.average().ifPresent(System.out::print);

        //使用supplier 接口每次指向一个新的IntStream
        Supplier<IntStream> streamSupplier = () -> IntStream.of(1, 2, 3, 4, 5);
        long count = streamSupplier.get().count();
        System.out.println(count);
        streamSupplier.get().max().ifPresent(System.out::print);

        //149
        //public static IntStream stream(int[] array)
        Arrays.stream(new int[]{1, 2, 3}).map(x -> x * x).forEach(System.out::print);
    }


    @Test
    public void testCollect() {

        Supplier<Stream<Person>> streamSupplier = () -> Stream.of(new Person("郭源潮"), new Person("哈哈哈哈"), new Person("郭源潮"));
        Stream<Person> stream = Stream.of(new Person("郭源潮"), new Person("哈哈哈哈"), new Person("郭源潮"));

        List<Person> personList = streamSupplier.get().filter(x -> x.getName().startsWith("郭")).collect(toList());
        //[Person{name='郭源潮'}, Person{name='郭源潮'}]
        System.out.println(personList);

        //groupby 的条件将会作为map的key
        Map map = streamSupplier.get().collect(Collectors.groupingBy(p -> p.getName()));

        //{哈哈哈哈=[Person{name='哈哈哈哈'}], 郭源潮=[Person{name='郭源潮'}, Person{name='郭源潮'}]}
        System.out.println(map);

        //AAAA郭源潮|哈哈哈哈|郭源潮BBBBB
        String result = streamSupplier.get().map(person -> person.getName()).collect(Collectors.joining("|", "AAAA", "BBBBB"));
        //郭源潮|哈哈哈哈|郭源潮
        String result2 = streamSupplier.get().map(person -> person.getName()).collect(Collectors.joining("|"));
        System.out.println(result);
        System.out.println(result2);


        /**
         * 既然我们知道了一些最强大的内置收集器，让我们来尝试构建自己的特殊收集器吧。
         * 我们希望将流中的所有人转换为一个字符串，包含所有大写的名称，并以|分割。为了完成它，我们通过Collector.of()创建了一个新的收集器。
         * 我们需要传递一个收集器的四个组成部分：供应器、累加器、组合器和终止器。
         */

        /*of(Supplier<A> supplier,
                BiConsumer<A, T> accumulator,
                BinaryOperator<A> combiner,
                Function<A, R> finisher,
                Collector.Characteristics... characteristics)*/
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
    }


    /**
     * 传递给map方法的lambda为每个单词生成了一个String[](String列表)。因此，map返回的流实际上是Stream<String[]> 类型的
     * 你真正想要的是用Stream<String>来表示一个字符串。
     */
    @Test
    public void testflap() {
        String[] words = new String[]{"Hello", "World"};
        List<String[]> a = Arrays.stream(words).map(word -> word.split("")).distinct().collect(toList());
        a.forEach(System.out::print);
        //得到的是流的列表
        System.out.println(Arrays.stream(words).map(word -> word.split("")).map(Arrays::stream).distinct().collect(toList()));
    }

    /**
     * 使用flatMap方法的效果是，各个数组并不是分别映射一个流，而是映射成流的内容，所有使用map(Array::stream)时生成的单个流被合并起来，即扁平化为一个流。
     */
    @Test
    public void testflap2() {
        String[] words = new String[]{"Hello", "World"};
        List<String> a = Arrays.stream(words).map(word -> word.split("")).flatMap(Arrays::stream).distinct().collect(toList());
        a.forEach(System.out::print);
    }


    @Test
    public void flapMap() {
        //flatmap <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
        //map <R> Stream<R> map(Function<? super T, ? extends R> mapper);
        Person p1 = new Person("郭源潮");
        Person p2 = new Person("哈哈哈哈");
        Person p3 = new Person("郭源潮2");
        Son sun3 = new Son("sun");p3.setSon(sun3);

        Supplier<Stream<Person>> streamSupplier = () -> Stream.of(p1, p2, p3);
        streamSupplier.get().map(x -> Stream.of(x.getSon())).forEach(x -> x.forEach(System.out::println));
        System.out.println("============================");
        streamSupplier.get().flatMap(person -> Stream.of(person.getSon())).forEach(System.out::println);
        streamSupplier.get().flatMap(person -> Stream.of(person.getSon())).forEach(System.out::println);
    }

    @Test
    public void flapMap2() {
        //普通方式的map操作的是一个个对象 返回的也是一个个对象
        Person p1 = new Person("P1");
        Person p2 = new Person("P2");
        Person p3 = new Person("P3");
        Son sun1 = new Son("S1");
        Son sun2 = new Son("S2");
        Son sun3 = new Son("S3");
        List<Son> sonList = Arrays.asList(sun1, sun2, sun3);
        p1.setSonList(sonList);
        p2.setSonList(sonList);
        p3.setSonList(sonList);

        Stream<Person> personStrem = Stream.of(p1, p2, p3);
        //经典方式使用map获Son of Person forEach 里面每个都是一个List<Son>
        personStrem
                .map(x -> {
                    System.out.println(x.getName());
                    return Stream.of(x.getSonList());
                })
                .forEach(x -> x.forEach(System.out::println));

        //注意不要在map return 中使用Stream.of
        Stream<Person> personStrem2 = Stream.of(p1, p2, p3);
        personStrem2
                .map(x -> {
                    System.out.println(x.getName());
                    return x.getSonList().stream();
                })
                //Stream(List<Son>).forEach(List<Son> x.forEach(Son son)
                .forEach(x -> x.forEach(y -> System.out.println(y.getName())));
        //.forEach(x->x.forEach(y->y.forEach(System.out::println)));


        Stream<Person> personStrem3 = Stream.of(p1, p2, p3);
        personStrem3.flatMap(x -> x.getSonList().stream()).forEach(s -> System.out.println(s.getName()));
    }


    @Test
    public void forkJoin() {
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
    public void sort() {

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
    /*并行*/
    @Test
    public void testBingXing() {
        List<Human> persons = Arrays.asList(
                new Human("Max", 18),
                new Human("Peter", 23),
                new Human("Pamela", 23),
                new Human("David", 12));

        Integer totalAge = persons
                .parallelStream()
                .reduce(0,//初始值
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s [%s]\n",
                                    sum, p, Thread.currentThread().getName());
                            return sum += p.getAge();
                        },//累加器
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
                                    sum1, sum2, Thread.currentThread().getName());
                            return sum1 + sum2;
                        });//组合器

        System.out.println("==============================================" + totalAge);

        Integer totalAge2 = persons
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
        System.out.println("==============================================" + totalAge2);
    }


    @Setter
    @Getter
    @AllArgsConstructor
    public class Human {

        private String name;
        private int age;

        @Override
        public String toString() {
            return "Human{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @AllArgsConstructor
    @Data
    public class Person {

        private String name;

        private Son son;

        private List<Son> sonList;

        public Person(String name) {
            this.name = name;
        }

    }


    @AllArgsConstructor
    @Data
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


