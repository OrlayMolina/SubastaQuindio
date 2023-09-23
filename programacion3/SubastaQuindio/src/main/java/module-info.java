module co.edu.uniquindio.programacion3.subastaquindio {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens co.edu.uniquindio.programacion3.subastaquindio to javafx.fxml;
    exports co.edu.uniquindio.programacion3.subastaquindio;
}