module MedicalCentre {
    requires com.zaxxer.hikari;
    requires java.sql;
    requires javafx.graphics;
    requires com.microsoft.sqlserver.jdbc;
    requires javafx.controls;
    requires javafx.fxml;

    exports com.medicalproject;
    opens com.medicalproject to javafx.fxml;
    exports com.medicalproject.Controllers;
    opens com.medicalproject.Controllers to javafx.fxml;
}