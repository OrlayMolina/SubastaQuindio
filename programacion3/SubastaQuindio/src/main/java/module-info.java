module co.edu.uniquindio.programacion3.subastaquindio {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.mapstruct;

    opens co.edu.uniquindio.programacion3.subastaquindio to javafx.fxml;
    exports co.edu.uniquindio.programacion3.subastaquindio;
    opens co.edu.uniquindio.programacion3.subastaquindio.controller to javafx.fxml;
    exports co.edu.uniquindio.programacion3.subastaquindio.controller;
    opens co.edu.uniquindio.programacion3.subastaquindio.viewController to javafx.fxml;
    exports co.edu.uniquindio.programacion3.subastaquindio.viewController;
    opens co.edu.uniquindio.programacion3.subastaquindio.mapping.dto to javafx.fxml;
    exports co.edu.uniquindio.programacion3.subastaquindio.mapping.dto;
    opens co.edu.uniquindio.programacion3.subastaquindio.mapping.mappers to javafx.fxml;
    exports co.edu.uniquindio.programacion3.subastaquindio.mapping.mappers;
    opens co.edu.uniquindio.programacion3.subastaquindio.model to javafx.fxml;
    exports co.edu.uniquindio.programacion3.subastaquindio.model;

}