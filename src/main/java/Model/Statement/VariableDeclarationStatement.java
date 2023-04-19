package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.Value;

public class VariableDeclarationStatement implements IStatement {

    String name;
    Type type;

    public VariableDeclarationStatement(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        typeEnv.put(name, type);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        //MyIStack<IStmt> stack = state.getExeStack();
        //stack.pop();
        //state.setExeStack(stack);
        MyIDict<String, Value> dict = state.getSymTable();
        if(dict.isDefined(name))
            throw new MyException("The variable is already defined.");
        dict.put(name, type.defaultValue());
        state.setSymTable(dict);
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type.toString(), name);
    }
}
