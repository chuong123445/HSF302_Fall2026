module fu.de200388.demologin {
    requires javafx.controls;
    requires javafx.fxml;


    opens fu.de200388.demologin to javafx.fxml;
    exports fu.de200388.demologin;
    exports fu.de200388.demologin.b1;
    exports fu.de200388.demologin.b2;
    exports fu.de200388.demologin.b3;
    exports fu.de200388.demologin.b4;
    opens fu.de200388.demologin.b4 to javafx.fxml;
}
