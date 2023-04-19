package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.ADT.MyILatchTable;
import Model.Expression.IExp;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStatement implements IStatement {
    private final String var;
    private final IExp expression;
    private static final Lock lock = new ReentrantLock();

    public NewLatchStatement(String var, IExp expression) {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        lock.lock();
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        MyILatchTable latchTable = state.getLatchTable();
        IntValue nr = (IntValue) (expression.eval(symTable, heap));
        int number = nr.getValue();
        int freeAddress = latchTable.getFreeAddress();
        latchTable.put(freeAddress, number);
        if (symTable.isDefined(var)) {
            symTable.update(var, new IntValue(freeAddress));
        } else {
            throw new Exception(String.format("%s is not defined in the symbol table!", var));
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        try {
            if (typeEnv.lookUp(var).equals(new IntType())) {
                if (expression.typecheck(typeEnv).equals(new IntType())) {
                    return typeEnv;
                } else {
                    throw new MyException("Expression doesn't have the int type!");
                }
            } else {
                throw new MyException(String.format("%s is not of int type!", var));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return String.format("newLatch(%s, %s)", var, expression);
    }
}
