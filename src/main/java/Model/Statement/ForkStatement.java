package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIStack;
import Model.ADT.MyStack;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.Value;

public class ForkStatement implements IStatement {

    private IStatement statement;

    public ForkStatement(IStatement statement)
    {
        this.statement = statement;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        try {
            statement.typecheck(typeEnv.copy());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyIStack<IStatement> newExeStack = new MyStack<>();
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIDict<String, Value> newSymTable = symTable.copy();

        return new ProgramState(newExeStack, newSymTable, state.getOut(), statement, state.getFileTable(), state.getHeap(), state.getLatchTable());
    }

    @Override
    public String toString(){
        return "fork( " + this.statement + " );";
    }
}
