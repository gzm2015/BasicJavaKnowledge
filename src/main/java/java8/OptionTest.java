package java8;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.util.*;

public class OptionTest {

    List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");
    @Test
    public void optional() {
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



    @Test
    public void testOptionList(){

        List<BasicOp> nullList = null;

        List<BasicOp> emptyList = new ArrayList<>();

        BasicOp first = Optional.ofNullable(nullList).flatMap(basicOps -> Optional.ofNullable(basicOps.get(0))).orElse(null);
        BasicOp second = Optional.ofNullable(nullList).flatMap(basicOps ->basicOps.stream().findFirst()).orElse(null);
        Assert.assertEquals(null,first);
        Assert.assertEquals(null,second);
        String secondName = Optional.ofNullable(nullList).flatMap(basicOps -> Optional.ofNullable(basicOps.stream().findFirst().get().getSon().getName())).orElse(null);
        Assert.assertNull(secondName);
        String upperName = Optional.ofNullable(emptyList).flatMap(basicOps -> basicOps.stream().findFirst().flatMap(basicOp -> Optional.ofNullable(upperName(basicOp.getName())))).orElse(null);
        Assert.assertEquals(null,upperName);


        List<BasicOp> lsit = Optional.ofNullable(emptyList).flatMap(basicOps -> Optional.ofNullable(convert(basicOps))).orElse(null);



        BasicOp emptyListFirstObj = Optional.ofNullable(emptyList).flatMap(basicOps -> basicOps.stream().findFirst()).orElse(null);
        BasicOp nullListFirstObj = Optional.ofNullable(nullList).flatMap(basicOps -> basicOps.stream().findFirst()).orElse(null);

        Assert.assertNull(emptyListFirstObj);
        Assert.assertNull(nullListFirstObj);


        String emptyListFirstObjName = Optional.ofNullable(emptyList).flatMap(basicOps -> basicOps.stream().findFirst()).flatMap(basicOp -> Optional.ofNullable(basicOp.getSon())).flatMap(basicOp2 -> Optional.ofNullable(basicOp2.getName())).orElse(null);
        String nullListFirstObjName = Optional.ofNullable(nullList).flatMap(basicOps -> basicOps.stream().findFirst()).flatMap(basicOp -> Optional.ofNullable(basicOp.getSon())).flatMap(basicOp2 -> Optional.ofNullable(basicOp2.getName())).orElse(null);


        Assert.assertNotNull(Optional.of(emptyList).orElse(null));

        //Optional.of(nullList);
    }

    @Test
    public void testOpRegion(){
        BasicOp nullObj = null;
        String upperName =Optional.ofNullable(nullObj).flatMap(basicOp -> Optional.ofNullable(basicOp.getName())).flatMap(s -> Optional.ofNullable(upperName(s))).orElse(null);
        Assert.assertEquals(null,upperName);

        String sonName = Optional.ofNullable(nullObj).flatMap(basicOp -> Optional.ofNullable(basicOp.getSon())).flatMap(basicOp2 -> Optional.ofNullable(basicOp2.getName())).orElse(null);

    }

    @Data
    class BasicOp {
        private String name;
        private BasicOp2 son;
    }

    @Data
    class BasicOp2 {
        private String name;
    }

    String upperName(String name){
        return name.toUpperCase();
    }

    List<BasicOp> convert(List<BasicOp> list){
        return list;
    }



    @Test
    public void test(){

        Date date= null;

        String date2 = Optional.ofNullable(date).flatMap(date1 -> Optional.ofNullable( DateFormat.getDateInstance().format(date))).orElse("");
        System.out.println(date2);

    }
}
