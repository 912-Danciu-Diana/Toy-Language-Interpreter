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

public class IfStatement implements IStatement {
    IExp exp;
    IStatement thenStatement;
    IStatement elseStatement;

    public IfStatement(IExp exp, IStatement thenStatement, IStatement elseStatement) {
        this.exp = exp;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public MyIDict<String, Type> typecheck(MyIDict<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType()))
        {
            try {
                thenStatement.typecheck(typeEnv.copy());
                elseStatement.typecheck(typeEnv.copy());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return typeEnv;
        }
        else throw new MyException("The condition of IF has not the type bool");
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        Value cond = exp.eval(state.getSymTable(), state.getHeap());
        if(cond.getType().equals(new BoolType()))
        {
            BoolValue boolCond = (BoolValue) cond;
            if(boolCond.getValue()==true)
            {
                MyIStack<IStatement> stack = state.getExeStack();
                stack.push(thenStatement);
                state.setExeStack(stack);
                return null;
            }
            else
            {
                MyIStack<IStatement> stack = state.getExeStack();
                stack.push(elseStatement);
                state.setExeStack(stack);
                return null;
            }
        } throw new MyException("Conditional expr is not a boolean.");
    }

    @Override
    public String toString(){ return "(IF("+ exp.toString()+") THEN(" +thenStatement.toString()+")ELSE("+elseStatement.toString()+"))";}
}
