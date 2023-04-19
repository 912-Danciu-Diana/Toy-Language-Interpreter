package Model.ADT;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws Exception;
    void push(T element);
    boolean isEmpty();
    List<T> getReversed();
}
