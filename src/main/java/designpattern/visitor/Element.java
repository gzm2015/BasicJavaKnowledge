package designpattern.visitor;

import java.util.ArrayList;
import java.util.List;

public interface Element {
    void accept(Visitor visitor);
}

