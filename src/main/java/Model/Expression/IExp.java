package Model.Expression;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Type.Type;
import Model.Value.Value;

public interface IExp {
    Type typecheck(MyIDict<String,Type> typeEnv) throws MyException;
    Value eval(MyIDict<String, Value> table, MyIHeap heap) throws Exception;
}
