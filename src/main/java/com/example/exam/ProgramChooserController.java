package com.example.exam;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.util.ArrayList;
import java.util.List;

public class ProgramChooserController {
    private ProgramExecutorController programExecutorController;

    public void setProgramExecutorController(ProgramExecutorController programExecutorController) {
        this.programExecutorController = programExecutorController;
    }
    @FXML
    private ListView<IStatement> programsListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        programsListView.setItems(getAllStatements());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(ActionEvent actionEvent) {
        IStatement selectedStatement = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            int id = programsListView.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typecheck(new MyDict<>());
                ProgramState programState = new ProgramState(new MyStack<>(), new MyDict<>(), new MyList<>(), selectedStatement, new MyDict<>(), new MyHeap(), new MyLatchTable());
                IRepo repository = new Repo(programState, "log" + (id + 1) + ".txt");
                Controller controller = new Controller(repository);
                programExecutorController.setController(controller);
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private ObservableList<IStatement> getAllStatements() {
        List<IStatement> allStatements = new ArrayList<>();

        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        allStatements.add(ex1);

        IStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                        new CompoundStatement(new AssignStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                new PrintStatement(new VariableExpression("b"))))));
        allStatements.add(ex2);

        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                        new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                new PrintStatement(new VariableExpression("v"))))));
        allStatements.add(ex3);

        IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("C:/Users/danad/IdeaProjects/lab_3/src/View/test.in.txt"))),
                        new CompoundStatement(new OpenRFile(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))))))))));
        allStatements.add(ex4);

        IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));
        allStatements.add(ex5);

        IStatement ex6 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new ReadHeapExp(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression('+',new ReadHeapExp(new ReadHeapExp(new VariableExpression("a"))), new ValueExpression(new IntValue(5)))))))));
        allStatements.add(ex6);

        IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement( new PrintStatement(new ReadHeapExp(new VariableExpression("v"))),
                                new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+', new ReadHeapExp(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));
        allStatements.add(ex7);

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
        allStatements.add(ex8);

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
        allStatements.add(ex9);

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
        allStatements.add(ex10);

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
        allStatements.add(ex11);

        IStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new ConditionalAssignmentStatement("b",
                                        new VariableExpression("a"),
                                        new ValueExpression(new IntValue(100)),
                                        new ValueExpression(new IntValue(200))),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("b")),
                                                new CompoundStatement(new ConditionalAssignmentStatement("b",
                                                        new ValueExpression(new BoolValue(false)),
                                                        new ValueExpression(new IntValue(100)),
                                                        new ValueExpression(new IntValue(200))),
                                                        new PrintStatement(new VariableExpression("b"))))))));
        allStatements.add(ex12);

        return FXCollections.observableArrayList(allStatements);
    }
}
