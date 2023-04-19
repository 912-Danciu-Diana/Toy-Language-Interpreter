package Model.ADT;

import Exception.MyException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    List<T> list;

    public MyList()
    {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        this.list.add(element);
    }

    @Override
    public T pop() throws MyException {
        if(isEmpty())
            throw new MyException("Cannot pop any element from an empty list.");
        return this.list.remove(0);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public List<T> getList() {
        //synchronized(this) {return..}
        return list;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
