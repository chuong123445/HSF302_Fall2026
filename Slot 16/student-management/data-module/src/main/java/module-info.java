module fu.de200388.datamodule {
    requires javafx.controls;
    requires javafx.fxml;


    opens fu.de200388.datamodule to javafx.fxml;
    exports fu.de200388.datamodule;
}