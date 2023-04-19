package Model;

import Exception.MyException;
import Model.ADT.*;
import Model.Statement.IStatement;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.util.List;


public class ProgramState {
    private MyIStack<IStatement> exeStack;
    private MyIDict<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDict<StringValue, BufferedReader> filetable;
    private MyIHeap heap;
    private MyILatchTable latchTable;
    private int id;
    private static int lastId = 0;

    public ProgramState(MyIStack<IStatement> stack, MyIDict<String, Value> symTable, MyIList<Value> out, IStatement program, MyIDict<StringValue, BufferedReader> filetable, MyIHeap heap, MyILatchTable latchTable) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.exeStack.push(program);
        this.filetable = filetable;
        this.heap = heap;
        this.latchTable = latchTable;
        this.id = setId();
    }

    public synchronized int setId() {
        lastId++;
        return lastId;
    }

    public void setExeStack(MyIStack<IStatement> newStack) {
        this.exeStack = newStack;
    }

    public void setSymTable(MyIDict<String, Value> newSymTable) {
        this.symTable = newSymTable;
    }

    public void setOut(MyIList<Value> newOut) {
        this.out = newOut;
    }

    public void setFileTable(MyIDict<StringValue, BufferedReader> newFileTable) {
        this.filetable = newFileTable;
    }

    public void setHeap(MyIHeap newHeap) { this.heap = newHeap; }

    public void setLatchTable(MyILatchTable newLatchTable) {
        this.latchTable = newLatchTable;
    }

    public MyIStack<IStatement> getExeStack() {return this.exeStack;}

    public MyIDict<String, Value> getSymTable() {return this.symTable;}

    public MyIList<Value> getOut() {return this.out;}

    public MyIDict<StringValue, BufferedReader> getFileTable() {
        return filetable;
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public MyILatchTable getLatchTable() {
        return latchTable;
    }

    public int getId() {
        return this.id;
    }

    public String exeStackToString(){
        StringBuilder exeStackStringBuilder = new StringBuilder();
        List <IStatement> stack = exeStack.getReversed();
        for(IStatement statement: stack)
        {
            exeStackStringBuilder.append(statement.toString()).append("\n");
        }
        return exeStackStringBuilder.toString();
    }

    public String symTableToString() throws Exception {
        StringBuilder symTableStringBuilder = new StringBuilder();
        for(String key: symTable.keySet())
        {
            symTableStringBuilder.append(String.format("%s -> %s\n", key, symTable.lookUp(key).toString()));
        }
        return symTableStringBuilder.toString();
    }

    public String outToString(){
        StringBuilder outStringBuilder = new StringBuilder();
        for(Value elem: out.getList())
        {
            outStringBuilder.append(String.format("%s\n",elem.toString()));
        }
        return outStringBuilder.toString();
    }

    public String fileTableToString(){
        StringBuilder fileTableStringBuilder = new StringBuilder();
        for (StringValue key: filetable.keySet()) {
            fileTableStringBuilder.append(String.format("%s\n", key));
        }
        return fileTableStringBuilder.toString();
    }

    public String heapToString(){
        StringBuilder heapStringBuilder = new StringBuilder();
        for (Integer key: heap.keySet()){
            try {
                heapStringBuilder.append(String.format("%d -> %s\n", key, heap.get(key)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return heapStringBuilder.toString();
    }

    public String latchTableToString() throws Exception {
        StringBuilder latchTableStringBuilder = new StringBuilder();
        for (int key: latchTable.getLatchTable().keySet()) {
            latchTableStringBuilder.append(String.format("%d -> %d\n", key, latchTable.get(key)));
        }
        return latchTableStringBuilder.toString();
    }

    public Boolean isNotCompleted()
    {
        if(exeStack.isEmpty())
            return false;
        return true;
    }

    public ProgramState oneStep() throws Exception{
        if(exeStack.isEmpty()) throw new MyException("prgstate stack is empty");
        IStatement crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString() {
        try {
            return "Id: " + id +"\nExeStack:\n" + this.exeStackToString() + "SymTable:\n" + this.symTableToString() + "Out:\n" +
                    this.outToString() + "Heap:\n" + this.heapToString() + "FileTable:\n" + this.fileTableToString() + "\nLatch table:\n" + this.latchTableToString() + "\n";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
