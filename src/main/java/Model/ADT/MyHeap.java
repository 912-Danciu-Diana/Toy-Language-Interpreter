package Model.ADT;

import Exception.MyException;
import Model.Value.Value;

import java.util.HashMap;
import java.util.Set;

public class MyHeap implements MyIHeap{

    HashMap<Integer, Value> heap;
    Integer freeLocationValue;

    public MyHeap()
    {
        this.heap = new HashMap<>();
        freeLocationValue = 1;
    }

    public int newValue()
    {
        freeLocationValue += 1;
        while(freeLocationValue == 0 || this.containsKey(freeLocationValue))
            freeLocationValue += 1;
        return freeLocationValue;
    }

    @Override
    public int getFreeValue() {
        return freeLocationValue;
    }

    @Override
    public HashMap<Integer, Value> getContent() {
        return heap;
    }

    @Override
    public void setContent(HashMap<Integer, Value> newMap) {
        this.heap = newMap;
    }

    @Override
    public int add(Value value) {
        heap.put(freeLocationValue, value);
        Integer toReturn = freeLocationValue;
        freeLocationValue = this.newValue();
        return toReturn;
    }

    @Override
    public void update(Integer position, Value value) throws Exception{
        if(!heap.containsKey(position))
            throw new MyException(String.format("Position %d is not mapped in the heap.", position));
        heap.put(position, value);
    }

    @Override
    public Value get(Integer position) throws MyException {
        if(!heap.containsKey(position))
            throw new MyException(String.format("Position %d is not mapped in the heap.", position));
        return heap.get(position);
    }

    @Override
    public boolean containsKey(Integer position) {
        return heap.containsKey(position);
    }

    @Override
    public void remove(Integer key) throws MyException {
        if(!heap.containsKey(key))
            throw new MyException(String.format("Key %d is not defined.", key));
        freeLocationValue = key;
        heap.remove(key);
    }

    @Override
    public Set<Integer> keySet() {
        return heap.keySet();
    }

    @Override
    public String toString()
    {
        return heap.toString();
    }
}
