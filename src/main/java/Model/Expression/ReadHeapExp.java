package Model.Expression;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class ReadHeapExp implements IExp{

    private IExp expression;

    public ReadHeapExp(IExp expression)
    {
        this.expression = expression;
    }

    @Override
    public Type typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        Type typ = expression.typecheck(typeEnv);
        if(typ instanceof RefType)
        {
            RefType refTyp = (RefType) typ;
            return refTyp.getInner();
        }
        else throw new MyException("Operand is not of RefType.");
    }

    @Override
    public Value eval(MyIDict<String, Value> table, MyIHeap heap) throws Exception {
        Value evaluated = expression.eval(table, heap);
        if(evaluated instanceof RefValue)
        {
            int address = ((RefValue) evaluated).getAddress();
            Value value = heap.get(address);
            return value;
        }
        else
            throw new MyException(String.format("%s not of RefValue", evaluated));
    }

    @Override
    public String toString() {
        return String.format("ReadHeap(%s)", expression);
    }
}
