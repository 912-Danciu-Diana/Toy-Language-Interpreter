package Model.ADT;

import Exception.MyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDict<T1,T2> implements MyIDict<T1,T2> {

    HashMap<T1, T2> dictionary;

    public MyDict(){
        this.dictionary = new HashMap<>();
    }

    @Override
    public void put(T1 key, T2 value) {
        this.dictionary.put(key,value);
    }

    @Override
    public boolean isDefined(T1 key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public T2 lookUp(T1 key) throws Exception {
        if(!(isDefined(key)))
            throw new Exception("The key" + key + "is not found in the dictionary.");
        return this.dictionary.get(key);
    }

    @Override
    public void remove(T1 key) throws MyException {
        if(!(isDefined(key)))
            throw new MyException("The key" + key + "is not found in the dictionary.");
        this.dictionary.remove(key);
    }

    @Override
    public void update(T1 key, T2 value) throws MyException {
        if (!isDefined(key))
            throw new MyException(key + " is not defined.");
        this.dictionary.put(key, value);
    }

    @Override
    public Set<T1> keySet() {
        return dictionary.keySet();
    }

    @Override
    public Collection<T2> values() {
        return this.dictionary.values();
    }

    @Override
    public Map<T1, T2> getContent() {
        return this.dictionary;
    }

    @Override
    public MyIDict<T1, T2> copy() throws Exception {
        MyIDict<T1, T2> copyDict = new MyDict<>();
        for(T1 key: keySet())
        {
            copyDict.put(key, lookUp(key));
        }
        return copyDict;
    }

    @Override
    public String toString() {
        return this.dictionary.toString();
    }

}
