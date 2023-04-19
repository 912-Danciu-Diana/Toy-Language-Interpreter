package Model.Statement;

import Exception.MyException;
import Model.ADT.MyIDict;
import Model.ADT.MyIStack;
import Model.Expression.IExp;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class WhileStatement implements IStatement {

    private IExp expression;
    private IStatement statement;

    public WhileStatement(IExp expression, IStatement statement)
    {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        Type typeExpr = expression.typecheck(typeEnv);
        if (typeExpr.equals(new BoolType())) {
            try {
                statement.typecheck(typeEnv.copy());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return typeEnv;
        } else
            throw new MyException("The condition of WHILE does not have the type Bool.");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        MyIStack<IStatement> exeStack = state.getExeStack();
        if(value.getType().equals(new BoolType()))
        {
            BoolValue boolValue = (BoolValue) value;
            if(boolValue.getValue())
            {
                exeStack.push(this);
                exeStack.push(statement);
            }
        }
        else throw new MyException(String.format("%s not of BoolType", value));
        return null;
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }
}
