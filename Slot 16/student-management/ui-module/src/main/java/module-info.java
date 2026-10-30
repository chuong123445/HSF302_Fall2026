module fu.de200388.uimodule {
    requires javafx.controls;
    requires javafx.fxml;


    opens fu.de200388.uimodule to javafx.fxml;
    exports fu.de200388.uimodule;
}