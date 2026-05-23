module EcoWallet {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens app to javafx.fxml, javafx.graphics;
    opens model to javafx.base, com.google.gson;
    opens controller to javafx.fxml;

    exports app;
    exports model;
    exports service;
    exports factory;
}