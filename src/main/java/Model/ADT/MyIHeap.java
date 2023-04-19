package Model.ADT;

import Model.Value.Value;

import java.util.HashMap;
import java.util.Set;

public interface MyIHeap {
    int getFreeValue();
    HashMap<Integer, Value> getContent();
    void setContent(HashMap<Integer, Value> newMap);
    int add(Value value);
    void update(Integer position, Value value) throws Exception;
    Value get(Integer position) throws Exception;
    boolean containsKey(Integer position);
    void remove(Integer key) throws Exception;
    Set<Integer> keySet();
}
