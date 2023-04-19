package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ProgramState;
import Model.Type.Type;

public class NoOpStatement implements IStatement {
    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return null;
    }
    @Override
    public String toString() {
        return "NopStatement";
    }
}
