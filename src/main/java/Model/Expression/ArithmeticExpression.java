package Model.Expression;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class ArithmeticExpression implements IExp{

    IExp e1;
    IExp e2;
    char op;

    public ArithmeticExpression(char op, IExp e1, IExp e2)
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
        if(typ1.equals(new IntType()))
        {
            if(typ2.equals(new IntType()))
            {
                return new IntType();
            }
            else
                throw new MyException("Second operand is not an int.");
        }
        else
            throw new MyException("First operand is not an int.");
    }

    @Override
    public Value eval(MyIDict<String, Value> table, MyIHeap heap) throws Exception {
        Value v1, v2;
        v1 = e1.eval(table, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(table, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if (op == '+') return new IntValue(n1 + n2);
                if (op == '-') return new IntValue(n1 - n2);
                if (op == '*') return new IntValue(n1 * n2);
                if (op == '/')
                    if (n2 == 0) throw new MyException("division by zero");
                    else return new IntValue(n1 / n2);
            } else throw new MyException("Second operand is not an integer.");
        } else throw new MyException("First operand is not an integer.");
        return null;
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
}
