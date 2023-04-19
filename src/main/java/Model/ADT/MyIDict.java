package Model.ADT;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyIDict<T1,T2> {
    void put(T1 key, T2 value);
    boolean isDefined(T1 key);
    T2 lookUp(T1 key) throws Exception;
    void remove(T1 key) throws Exception;
    void update(T1 key, T2 value) throws Exception;
    public Set<T1> keySet();
    Collection<T2> values();
    public Map<T1, T2> getContent();
    MyIDict<T1, T2> copy() throws Exception;
}
