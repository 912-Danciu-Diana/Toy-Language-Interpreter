package Model.Statement;

import Model.ADT.MyIDict;
import Model.ADT.MyILatchTable;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Exception.MyException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStatement implements IStatement {
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public AwaitStatement(String var) {
        this.var = var;
    }
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();
        MyIDict<String, Value> symTable = state.getSymTable();
        MyILatchTable latchTable = state.getLatchTable();
        if (symTable.isDefined(var)) {
            IntValue fi = (IntValue) symTable.lookUp(var);
            int foundIndex = fi.getValue();
            if (latchTable.containsKey(foundIndex)) {
                if (latchTable.get(foundIndex) != 0) {
                    state.getExeStack().push(this);
                }
            } else {
                throw new Exception("Index not found in the latch table!");
            }
        } else {
            throw new Exception("Variable not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        try {
            if (typeEnv.lookUp(var).equals(new IntType()))
                return typeEnv;
            else
                throw new MyException(String.format("%s is not of int type!", var));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return String.format("await(%s)", var);
    }
}
