package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.Expression.IExp;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class ReadFile implements IStatement {

    private IExp expression;
    private String var_name;

    public ReadFile(IExp exp, String var_name)
    {
        this.expression = exp;
        this.var_name = var_name;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        try{
            if (expression.typecheck(typeEnv).equals(new StringType()))
                if (typeEnv.lookUp(var_name).equals(new IntType()))
                    return typeEnv;
                else
                    throw new MyException("ReadFile requires an int as its variable parameter.");
            else
                throw new MyException("ReadFile requires a string as es expression parameter.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (symTable.isDefined(var_name)) {
            Value value = symTable.lookUp(var_name);
            if (value.getType().equals(new IntType())) {
                value = expression.eval(symTable, state.getHeap());
                if (value.getType().equals(new StringType())) {
                    StringValue castValue = (StringValue) value;
                    if (fileTable.isDefined(castValue)) {
                        BufferedReader br = fileTable.lookUp(castValue);
                        try {
                            String line = br.readLine();
                            if(line == null)
                                line = "0";
                            symTable.put(var_name, new IntValue(Integer.parseInt(line)));
                            state.setSymTable(symTable);
                        } catch (Exception e)
                        {
                            throw new MyException(e.getMessage());
                        }
                    } else {
                        throw new MyException(String.format("The file table does not contain %s", castValue));
                    }
                } else {
                    throw new MyException(String.format("%s does not evaluate to StringType", value));
                }
            } else {
                throw new MyException(String.format("%s is not of type IntType", value));
            }
        } else {
            throw new MyException(String.format("%s is not present in the symTable.", var_name));
        }
        return null;
    }

    @Override
    public String toString(){
        return String.format("ReadFile(%s,%s)", expression.toString(), var_name);
    }
}
