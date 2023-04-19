package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIHeap;
import Model.Expression.IExp;
import Model.ProgramState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class WriteHeapStatement implements IStatement {

    private String var_name;
    private IExp expression;

    public WriteHeapStatement(String var_name, IExp expression)
    {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        try{
            if (typeEnv.lookUp(var_name).equals(new RefType(expression.typecheck(typeEnv))))
                return typeEnv;
            else
                throw new MyException("WriteHeap: right hand side and left hand side have different types.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if(symTable.isDefined(var_name))
        {
            Value value = symTable.lookUp(var_name);
            if(value instanceof RefValue)
            {
                RefValue refValue = (RefValue) value;
                Value evaluated = expression.eval(symTable, heap);
                if(evaluated.getType().equals(refValue.getLocationType()))
                {
                    heap.update(refValue.getAddress(), evaluated);
                    state.setHeap(heap);
                    return null;
                }
                throw new MyException(String.format("%s not of %s", evaluated, refValue.getLocationType()));
            }
            else throw new MyException(String.format("%s not of RefType", value));
        }
        else throw new MyException(String.format("%s not defined in symTable", var_name));
    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", var_name, expression);
    }
}
