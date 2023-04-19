package Model.ADT;

import java.util.List;

public interface MyIList<T>{
    void add(T element);
    T pop() throws Exception;
    boolean isEmpty();
    List<T> getList();
}
