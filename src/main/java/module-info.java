module ma.proj.testexam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports ma.proj.testexam.controller;
    opens ma.proj.testexam.controller to javafx.fxml;
    opens ma.proj.testexam to javafx.fxml;
    exports ma.proj.testexam;
}