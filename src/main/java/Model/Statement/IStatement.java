package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ProgramState;
import Model.Type.Type;

public interface IStatement {
    MyIDict<String, Type> typecheck(MyIDict<String,Type> typeEnv) throws MyException;
    ProgramState execute(ProgramState state) throws Exception;
}
