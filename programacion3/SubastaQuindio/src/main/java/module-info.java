module co.edu.uniquindio.programacion3.subastaquindio {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens co.edu.uniquindio.programacion3.subastaquindio to javafx.fxml;
    exports co.edu.uniquindio.programacion3.subastaquindio;
    opens co.edu.uniquindio.programacion3.subastaquindio.controller to javafx.fxml;
    exports co.edu.uniquindio.programacion3.subastaquindio.controller;
    opens co.edu.uniquindio.programacion3.subastaquindio.viewController to javafx.fxml;
    exports co.edu.uniquindio.programacion3.subastaquindio.viewController;
}