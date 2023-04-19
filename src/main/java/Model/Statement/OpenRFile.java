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
import java.io.FileReader;

public class OpenRFile implements IStatement {

    private IExp expression;

    public OpenRFile(IExp exp)
    {
        this.expression = exp;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        if (expression.typecheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("OpenReadFile requires a string expression.");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if (value.getType().equals(new StringType())){
            StringValue fileName = (StringValue) value;
            MyIDict<StringValue, BufferedReader> fileTable = state.getFileTable();
            if(!fileTable.isDefined(fileName)){
                BufferedReader br;
                try
                {
                    br = new BufferedReader(new FileReader(fileName.getValue()));

                } catch (Exception e)
                {
                    System.out.println(e);
                    throw new MyException(e.getMessage());
                }
                fileTable.put(fileName, br);
                state.setFileTable(fileTable);
            }
            else throw new MyException(String.format("%s is already opened", fileName.getValue()));
        }
        else throw new MyException(String.format("%s does not evaluate to StringType", expression));
        return null;
    }

    @Override
    public String toString() {
        return String.format("OpenReadFile(%s)", expression.toString());
    }
}
