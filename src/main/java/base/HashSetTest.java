package base;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;


/**
 * 测试 equal hashcode
 * https://blog.csdn.net/justloveyou_/article/details/52464440
 * 所以equals()相等的情况下hashCode()肯定相等，但是hashCode()相等的情况下equals()不一定相等。
 * 详细笔记在有道里面
 **/
public class HashSetTest {

    @Test
    public void hashTest(){
        HashSet<Person> myset = new HashSet<Person>();
        Person p = new Person("li", "12");
        Person p2 = new Person("li", "12");
        myset.add(p);
        myset.add(p2);
        Iterator<Person> i = myset.iterator();
        while(i.hasNext()){
            Person b = i.next();
            System.out.println(b.toString());
        }
    }


    @Setter
    @Getter
    public class Person {

        private String name;
        private String age;

        public Person(String name, String age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person [name=" + name + ", age=" + age + "]";
        }


        @Override
        public int hashCode() {
            return this.name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Person) {
                Person p = (Person) obj;
                return (name.equals(p.getName()) && age.equals(p.getAge()));
            }
            return super.equals(obj);
        }
    }

}
