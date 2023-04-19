package Model.Expression;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.Objects;

public class RelationalExp implements IExp{

    IExp exp1;
    IExp exp2;
    String operator;

    public RelationalExp(IExp exp1, IExp exp2, String operator)
    {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = operator;
    }

    @Override
    public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        Type typ1;
        Type typ2;
        typ1 = exp1.typecheck(typeEnv);
        typ2 = exp2.typecheck(typeEnv);
        if(typ1.equals(new IntType()))
        {
            if(typ2.equals(new IntType()))
            {
                return new BoolType();
            }
            else throw new MyException("Second operand is not int.");
        }
        else throw new MyException("First operand is not int.");
    }

    @Override
    public Value eval(MyIDict<String, Value> table, MyIHeap heap) throws Exception {
        Value val1, val2;
        val1 = exp1.eval(table, heap);
        if(val1.getType().equals(new IntType()))
        {
            val2 = exp2.eval(table, heap);
            if(val2.getType().equals(new IntType()))
            {
                IntValue int1 = (IntValue) val1;
                IntValue int2 = (IntValue) val2;
                int i1, i2;
                i1 = int1.getValue();
                i2 = int2.getValue();
                if(Objects.equals(operator,"<"))
                    return new BoolValue(i1 < i2);
                else if (Objects.equals(this.operator, "<="))
                    return new BoolValue(i1 <= i2);
                else if (Objects.equals(this.operator, "=="))
                    return new BoolValue(i1 == i2);
                else if (Objects.equals(this.operator, "!="))
                    return new BoolValue(i1 != i2);
                else if (Objects.equals(this.operator, ">"))
                    return new BoolValue(i1 > i2);
                else if (Objects.equals(this.operator, ">="))
                    return new BoolValue(i1 >= i2);
            }
            else throw new MyException(String.format("%s not int",val2.toString()));
        }
        else throw new MyException(String.format("%s not int",val1.toString()));
        return null;
    }

    @Override
    public String toString()
    {
        return this.exp1.toString() + " " + this.operator + " " + this.exp2.toString();
    }
}
