package designpattern.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuMengKe
 * @create 2018-11-16-14:11
 */
class CustomerGroup {

    private List<Customer> customers = new ArrayList<>();

    void accept(Visitor visitor) {
        for (Customer customer : customers) {
            customer.accept(visitor);
        }
    }

    void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
