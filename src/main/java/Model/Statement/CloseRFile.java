package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.Expression.IExp;
import Model.ProgramState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class CloseRFile implements IStatement {

    private IExp expression;

    public CloseRFile(IExp exp)
    {
        this.expression = exp;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        if(expression.typecheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else throw new MyException("CloseReadFile requires a string expression.");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if(value.getType().equals(new StringType()))
        {
            StringValue filename = (StringValue) value;
            MyIDict<StringValue, BufferedReader> filetable = state.getFileTable();
            if(filetable.isDefined(filename))
            {
                BufferedReader br = filetable.lookUp(filename);
                try{
                    br.close();
                }
                catch(Exception e)
                {
                    throw new MyException(e.getMessage());
                }
                filetable.remove(filename);
                state.setFileTable(filetable);
            } else throw new MyException(String.format("%s is not present in the FileTable", value));
        } else throw new MyException(String.format("%s does not evaluate to StringValue.", expression));
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("CloseRFile(%s)", expression.toString());
    }
}
