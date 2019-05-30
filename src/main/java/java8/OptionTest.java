package java8;

import lombok.Data;
import org.apache.zookeeper.Op;
import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OptionTest {

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
