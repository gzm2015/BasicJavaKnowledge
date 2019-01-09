package designpattern.visitor;

/**
 * @author gzm2015
 * @create 2018-11-16-14:11
 */
public class Item implements Element {

    private String name;

    Item(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
