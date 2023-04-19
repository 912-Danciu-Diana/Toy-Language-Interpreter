package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIList;
import Model.Expression.IExp;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStatement implements IStatement {

    IExp exp;

    public PrintStatement(IExp exp)
    {
        this.exp = exp;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyIList<Value> out = state.getOut();
        //MyIStack<IStmt> stack = state.getExeStack();
        //stack.pop();
        out.add(exp.eval(state.getSymTable(), state.getHeap()));
        //state.setExeStack(stack);
        state.setOut(out);
        return null;
    }

    @Override
    public String toString(){ return "print(" +exp.toString()+")";}
}
