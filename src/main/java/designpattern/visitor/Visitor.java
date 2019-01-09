package designpattern.visitor;

/**
 * @author gzm2015
 * @create 2018-11-16-14:11
 */
public interface Visitor {
    void visit(Customer customer);

    void visit(Order order);

    void visit(Item item);
}
