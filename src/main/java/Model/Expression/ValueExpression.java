package Model.Expression;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExpression implements IExp {
    Value e;

    public ValueExpression(Value e)
    {
        this.e = e;
    }

    @Override
    public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public Value eval(MyIDict<String, Value> table, MyIHeap heap) throws Exception {
        return e;
    }

    @Override
    public String toString() {
        return this.e.toString();
    }
}
