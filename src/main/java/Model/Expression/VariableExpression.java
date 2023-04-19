package Model.Expression;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.Type;
import Model.Value.Value;

public class VariableExpression implements IExp{
    String id;

    public VariableExpression(String id)
    {
        this.id = id;
    }

    @Override
    public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        try {
            return typeEnv.lookUp(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Value eval(MyIDict<String, Value> table, MyIHeap heap) throws Exception {
        return table.lookUp(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
