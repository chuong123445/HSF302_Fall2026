package fu.de200388.demologin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import fu.de200388.demologin.b1.DrinkOrderApp;
import fu.de200388.demologin.b2.LoginLayoutApp;
import fu.de200388.demologin.b3.RegisterApp;
import fu.de200388.demologin.b4.LoginApp;
import fu.de200388.demologin.common.AlertUtil;
import javafx.stage.Stage;

import java.util.function.Supplier;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        Label title = new Label("CHAPTER 08 — JAVAFX");
        title.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");
        VBox root = new VBox(12, title, new Label("Chọn bài tập để mở trong cửa sổ riêng:"),
                exercise("B1 — Đặt đồ uống & sự kiện", DrinkOrderApp::new),
                exercise("B2 — Layout & đăng nhập bằng Java", LoginLayoutApp::new),
                exercise("B3 — Đăng ký & kiểm tra dữ liệu", RegisterApp::new),
                exercise("B4 — Đăng nhập bằng FXML & CSS", LoginApp::new));
        root.setPadding(new Insets(24));
        stage.setTitle("demoLogin — Bài 0, B1–B4");
        stage.setScene(new Scene(root, 460, 330));
        stage.setMinWidth(440);
        stage.setMinHeight(350);
        stage.show();
    }

    private Button exercise(String title, Supplier<Application> factory) {
        Button button = new Button(title);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(event -> {
            Stage exerciseStage = new Stage();
            try {
                factory.get().start(exerciseStage);
            } catch (Exception ex) {
                exerciseStage.close();
                AlertUtil.error("Không mở được bài tập: " + ex.getMessage());
            }
        });
        return button;
    }
}
