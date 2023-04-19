package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.Expression.IExp;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStatement implements IStatement {

    String id;
    IExp exp;

    public AssignStatement(String id, IExp exp)
    {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        try {
            Type typeVar = typeEnv.lookUp(id);
            Type typeExp = exp.typecheck(typeEnv);
            if(typeVar.equals(typeExp))
                return typeEnv;
            else throw new MyException("Assignment: right hand side and left hand side have different types.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyIDict<String, Value> dict = state.getSymTable();
        if(dict.isDefined(id))
        {
            Value value = exp.eval(dict, state.getHeap());
            Type typeID = (dict.lookUp(id)).getType();
            if(value.getType().equals(typeID))
            {
                dict.update(id, value);
            }
            else throw new MyException("The type of expression and the type of variable do not match.");
        }
        else throw new MyException("The variable is not declared.");
        state.setSymTable(dict);
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s = %s", id, exp.toString());
    }
}
