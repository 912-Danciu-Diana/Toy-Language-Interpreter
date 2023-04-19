package View;

import Controller.Controller;
import Model.ADT.*;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepo;
import Repository.Repo;

public class Interpreter {
    public static void main(String[] args){

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        //int v; v=2;Print(v)
        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        try {
            ex1.typecheck(new MyDict<>());
            ProgramState prg1 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex1, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo1 = new Repo(prg1,"log1.txt");
            Controller ctr1 = new Controller(repo1);
            menu.addCommand(new RunExampleCommand("1", ex1.toString(), ctr1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //int a;int b; a=2+3*5;b=a+1;Print(b)
        IStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                        new CompoundStatement(new AssignStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                new PrintStatement(new VariableExpression("b"))))));
        try {
            ex2.typecheck(new MyDict<>());
            ProgramState prg2 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex2, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo2 = new Repo(prg2,"log2.txt");
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                        new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                new PrintStatement(new VariableExpression("v"))))));
        try {
            ex3.typecheck(new MyDict<>());
            ProgramState prg3 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex3, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo3 = new Repo(prg3,"log3.txt");
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExampleCommand("3", ex3.toString(), ctr3));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /*
        string varf;
        varf="test.in";
        openRFile(varf);
        int varc;
        readFile(varf,varc);
        print(varc);
        readFile(varf,varc);
        print(varc)
        closeRFile(varf)
        */
        IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("C:/Users/danad/IdeaProjects/lab_3/src/View/test.in.txt"))),
                        new CompoundStatement(new OpenRFile(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))))))))));
        try {
            ex4.typecheck(new MyDict<>());
            ProgramState prg4 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex4, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo4 = new Repo(prg4, "log4.txt");
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExampleCommand("4", ex4.toString(), ctr4));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /*
        Ref int v;
        new(v,20);
        Ref Ref int a;
        new(a,v);
        print(v);
        print(a)
        */
        IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));
        try {
            ex5.typecheck(new MyDict<>());
            ProgramState prg5 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex5, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo5 = new Repo(prg5, "log5.txt");
            Controller ctr5 = new Controller(repo5);
            menu.addCommand(new RunExampleCommand("5", ex5.toString(), ctr5));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /*
        Ref int v;
        new(v,20);
        Ref Ref int a;
        new(a,v);
        print(rH(v));
        print(rH(rH(a))+5)
        */
        IStatement ex6 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new ReadHeapExp(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression('+',new ReadHeapExp(new ReadHeapExp(new VariableExpression("a"))), new ValueExpression(new IntValue(5)))))))));
        try {
            ex6.typecheck(new MyDict<>());
            ProgramState prg6 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex6, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo6 = new Repo(prg6, "log6.txt");
            Controller ctr6 = new Controller(repo6);
            menu.addCommand(new RunExampleCommand("6", ex6.toString(), ctr6));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /*
        Ref int v;
        new(v,20);
        print(rH(v));
        wH(v,30);
        print(rH(v)+5);
        */
        IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement( new PrintStatement(new ReadHeapExp(new VariableExpression("v"))),
                                new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+', new ReadHeapExp(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));
        try {
            ex7.typecheck(new MyDict<>());
            ProgramState prg7 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex7, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo7 = new Repo(prg7, "log7.txt");
            Controller ctr7 = new Controller(repo7);
            menu.addCommand(new RunExampleCommand("7", ex7.toString(), ctr7));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /*
        Ref int v;
        new(v,20);
        Ref Ref int a;
        new(a,v);
        new(v,30);
        print(rH(rH(a)))
        */
        IStatement declare_v = new VariableDeclarationStatement("v", new RefType(new IntType()));
        IStatement alloc_v_1 = new NewStatement("v", new ValueExpression(new IntValue(20)));
        IStatement declare_a = new VariableDeclarationStatement("a", new RefType(new RefType(new IntType())));
        IStatement alloc_a = new NewStatement("a", new VariableExpression("v"));
        IStatement alloc_v_2 = new NewStatement("v", new ValueExpression(new IntValue(30)));
        IExp read_a_1 = new ReadHeapExp(new VariableExpression("a"));
        IExp read_a_2 = new ReadHeapExp(read_a_1);
        IStatement print_a = new PrintStatement(read_a_2);
        IStatement ex8 = new CompoundStatement(declare_v, new CompoundStatement(alloc_v_1, new CompoundStatement(declare_a,
                new CompoundStatement(alloc_a, new CompoundStatement(alloc_v_2, print_a)))));
        try {
            ex8.typecheck(new MyDict<>());
            ProgramState prg8 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex8, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo8 = new Repo(prg8, "log8.txt");
            Controller ctr8 = new Controller(repo8);
            menu.addCommand(new RunExampleCommand("8", ex8.toString(), ctr8));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        /*
        int v;
        v=4;
        (while (v>0) print(v); v=v-1);
        print(v)
        */
        IStatement declare_v9 = new VariableDeclarationStatement("v", new IntType());
        IStatement assign_v = new AssignStatement("v", new ValueExpression(new IntValue(4)));
        IExp rel_expr = new RelationalExp(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">");
        IStatement print_v_1 = new PrintStatement(new VariableExpression("v"));
        IExp arithmetic_v = new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)));
        IStatement assign_v_2 = new AssignStatement("v", arithmetic_v);
        IStatement compoundStatement_v = new CompoundStatement(print_v_1, assign_v_2);
        IStatement whileStatement_v = new WhileStatement(rel_expr, compoundStatement_v);
        IStatement print_v_2 = new PrintStatement(new VariableExpression("v"));
        IStatement ex9 = new CompoundStatement(declare_v9, new CompoundStatement(assign_v, new CompoundStatement(whileStatement_v, print_v_2)));
        try {
            ex9.typecheck(new MyDict<>());
            ProgramState prg9 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex9, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo9 = new Repo(prg9, "log9.txt");
            Controller ctr9 = new Controller(repo9);
            menu.addCommand(new RunExampleCommand("9", ex9.toString(), ctr9));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
        IStatement declare_v_10 = new VariableDeclarationStatement("v", new IntType());
        IStatement declare_a_10 = new VariableDeclarationStatement("a", new RefType(new IntType()));
        IStatement assign_v_10 = new AssignStatement("v", new ValueExpression(new IntValue(10)));
        IStatement alloc_a_10 = new NewStatement("a", new ValueExpression(new IntValue(22)));
        IStatement write_a_10 = new WriteHeapStatement("a", new ValueExpression(new IntValue(30)));
        IStatement assign_v_10_2 = new AssignStatement("v", new ValueExpression(new IntValue(32)));
        IStatement print_v_10 = new PrintStatement(new VariableExpression("v"));
        IExp read_heap_a = new ReadHeapExp(new VariableExpression("a"));
        IStatement print_a_10 = new PrintStatement(read_heap_a);
        IStatement fork_10 = new ForkStatement(new CompoundStatement(write_a_10, new CompoundStatement(assign_v_10_2, new CompoundStatement(print_v_10, print_a_10))));
        IStatement ex10 = new CompoundStatement(declare_v_10, new CompoundStatement(declare_a_10, new CompoundStatement(assign_v_10, new CompoundStatement(alloc_a_10, new CompoundStatement(fork_10,
                new CompoundStatement(print_v_10, print_a_10))))));
        try {
            ex10.typecheck(new MyDict<>());
            ProgramState prg10 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex10, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo10 = new Repo(prg10, "log10.txt");
            Controller ctr10 = new Controller(repo10);
            menu.addCommand(new RunExampleCommand("10", ex10.toString(), ctr10));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        IStatement ex11 = new CompoundStatement(
                new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(
                        new VariableDeclarationStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(
                                new VariableDeclarationStatement("v3", new RefType(new IntType())),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("cnt", new IntType()),
                                        new CompoundStatement(
                                                new NewStatement("v1", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(
                                                        new NewStatement("v2", new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(
                                                                new NewStatement("v3", new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(
                                                                        new NewLatchStatement("cnt", new ReadHeapExp(new VariableExpression("v2"))),
                                                                        new CompoundStatement(
                                                                                new ForkStatement(
                                                                                        new CompoundStatement(
                                                                                                new WriteHeapStatement("v1", new ArithmeticExpression('*', new ReadHeapExp(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                                new CompoundStatement(
                                                                                                        new PrintStatement(new ReadHeapExp(new VariableExpression("v1"))),
                                                                                                        new CompoundStatement(
                                                                                                                new CountDownStatement("cnt"),
                                                                                                                new ForkStatement(
                                                                                                                        new CompoundStatement(
                                                                                                                                new WriteHeapStatement("v2", new ArithmeticExpression('*', new ReadHeapExp(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                                                new CompoundStatement(
                                                                                                                                        new PrintStatement(new ReadHeapExp(new VariableExpression("v2"))),
                                                                                                                                        new CompoundStatement(
                                                                                                                                                new CountDownStatement("cnt"),
                                                                                                                                                new ForkStatement(
                                                                                                                                                        new CompoundStatement(
                                                                                                                                                                new WriteHeapStatement("v3", new ArithmeticExpression('*', new ReadHeapExp(new VariableExpression("v3")), new ValueExpression(new IntValue(10)))),
                                                                                                                                                                new CompoundStatement(
                                                                                                                                                                        new PrintStatement(new ReadHeapExp(new VariableExpression("v3"))),
                                                                                                                                                                        new CountDownStatement("cnt")
                                                                                                                                                                )
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompoundStatement(
                                                                                        new AwaitStatement("cnt"),
                                                                                        new CompoundStatement(
                                                                                                new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                                new CompoundStatement(
                                                                                                        new CountDownStatement("cnt"),
                                                                                                        new PrintStatement(new ValueExpression(new IntValue(100)))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        try {
            ex11.typecheck(new MyDict<>());
            ProgramState prg11 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex11, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo11 = new Repo(prg11, "log11.txt");
            Controller ctr11 = new Controller(repo11);
            menu.addCommand(new RunExampleCommand("11", ex11.toString(), ctr11));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        IStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("b", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
                        new CompoundStatement(new AssignStatement("b", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new ConditionalAssignmentStatement("c",
                                        new VariableExpression("b"),
                                        new ValueExpression(new IntValue(100)),
                                        new ValueExpression(new IntValue(200))),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("c")),
                                                new CompoundStatement(new ConditionalAssignmentStatement("c",
                                                        new ValueExpression(new BoolValue(false)),
                                                        new ValueExpression(new IntValue(100)),
                                                        new ValueExpression(new IntValue(200))),
                                                        new PrintStatement(new VariableExpression("c"))))))));
        try {
            ex12.typecheck(new MyDict<>());
            ProgramState prg12 = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), ex12, new MyDict<>(), new MyHeap(), new MyLatchTable());
            IRepo repo12 = new Repo(prg12, "log12.txt");
            Controller ctr12 = new Controller(repo12);
            menu.addCommand(new RunExampleCommand("12", ex12.toString(), ctr12));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        menu.show();
    }
}
