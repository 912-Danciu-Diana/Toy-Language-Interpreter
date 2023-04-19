module com.example.demo {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.exam to javafx.fxml;
    exports com.example.exam;

    exports Controller;
    opens Controller to javafx.fxml;

    exports  Model.Expression;
    opens Model.Expression to javafx.fxml;

    //exports Model.ProgramState;
    //opens model.programState to javafx.fxml;

    exports Model.Statement;
    opens Model.Statement to javafx.fxml;

    exports Model.Type;
    opens Model.Type to javafx.fxml;

    exports Model.ADT;
    opens Model.ADT to javafx.fxml;

    exports Model.Value;
    opens Model.Value to javafx.fxml;

    exports Repository;
    opens Repository to javafx.fxml;
}