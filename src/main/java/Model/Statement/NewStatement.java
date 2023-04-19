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

public class NewStatement implements IStatement {

    private String var_name;
    private IExp expression;

    public NewStatement(String var_name, IExp expression)
    {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        Type typevar = null;
        try {
            typevar = typeEnv.lookUp(var_name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyIDict<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if(symTable.isDefined(var_name))
        {
            Value value = symTable.lookUp(var_name);
            if(value.getType() instanceof RefType)
            {
                Value evaluated = expression.eval(symTable, heap);
                Type locationType = ((RefValue)value).getLocationType();
                if(locationType.equals(evaluated.getType()))
                {
                    int newPosition = heap.add(evaluated);
                    symTable.put(var_name, new RefValue(newPosition, locationType));
                    state.setSymTable(symTable);
                    state.setHeap(heap);
                }
                else
                    throw new MyException(String.format("%s not of %s", var_name, evaluated.getType()));
            }
            else
                throw new MyException(String.format("%s not of RefType", value));
        }
        else
            throw new MyException(String.format("%s is not a variable in symTable.", var_name));
        return null;
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", var_name, expression);
    }
}
