package Model.ADT;

import Exception.MyException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {

    Stack<T> stack;

    public MyStack()
    {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() throws MyException {
        if(isEmpty())
            throw new MyException("Cannot pop any element from an empty stack.");
        return this.stack.pop();
    }

    @Override
    public void push(T element) {
        this.stack.push(element);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public List<T> getReversed() {
        List<T> list = Arrays.asList((T[]) stack.toArray());
        Collections.reverse(list);
        return list;
    }

    @Override
    public String toString() {
        return this.getReversed().toString();
    }
}
