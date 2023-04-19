package Model.Expression;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class LogicExp implements IExp{
    IExp e1;
    IExp e2;
    int op; //1-and, 2-or

    public LogicExp(IExp e1, IExp e2, int op)
    {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        Type typ1;
        Type typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if(typ1.equals(new BoolType()))
        {
            if(typ2.equals(new BoolType()))
            {
                return new BoolType();
            }
            else
                throw new MyException("Second operand is not bool.");
        }
        else throw new MyException("First operand is not bool.");
    }

    @Override
    public Value eval(MyIDict<String, Value> table, MyIHeap heap) throws Exception {
        Value v1, v2;
        Value res = new BoolValue(false);
        v1 = e1.eval(table, heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(table, heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                boolean B1, B2;
                B1 = b1.getValue();
                B2 = b2.getValue();
                if (op == 1) res = new BoolValue(B1&&B2);
                if (op == 2) res = new BoolValue(B1||B2);
            } else throw new MyException("Second operand is not a boolean value.");
        } else throw new MyException("First operand is not a boolean value.");
        return res;
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
}
