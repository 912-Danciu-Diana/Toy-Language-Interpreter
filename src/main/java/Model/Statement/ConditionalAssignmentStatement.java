package Model.Statement;

import Model.ADT.MyIDict;
import Model.ADT.MyIStack;
import Model.Expression.IExp;
import Model.Expression.VariableExpression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;
import Exception.MyException;

public class ConditionalAssignmentStatement implements IStatement {
    private final String variable;
    private final IExp expression1;
    private final IExp expression2;
    private final IExp expression3;

    public ConditionalAssignmentStatement(String variable, IExp expression1, IExp expression2, IExp expression3) {
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyIStack<IStatement> exeStack = state.getExeStack();
        IStatement converted = new IfStatement(expression1, new AssignStatement(variable, expression2), new AssignStatement(variable, expression3));
        exeStack.push(converted);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        Type variableType = new VariableExpression(variable).typecheck(typeEnv);
        Type type1 = expression1.typecheck(typeEnv);
        Type type2 = expression2.typecheck(typeEnv);
        Type type3 = expression3.typecheck(typeEnv);
        if (type1.equals(new BoolType()) && type2.equals(variableType) && type3.equals(variableType))
            return typeEnv;
        else
            throw new MyException("The conditional assignment is invalid!");
    }

    @Override
    public String toString() {
        return String.format("%s=(%s)? %s: %s", variable, expression1, expression2, expression3);
    }
}
