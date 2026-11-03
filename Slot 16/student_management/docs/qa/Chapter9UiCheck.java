import fu.de200388.ui.JavaFxApplication;
import fu.de200388.service.StudentService;
import fu.de200388.pojo.Student;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.context.ConfigurableApplicationContext;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.nio.file.*;

public class Chapter9UiCheck {
    static Throwable failure;
    public static void main(String[] args) {
        Application.launch(CheckApp.class, args);
        if (failure != null) throw new AssertionError("JavaFX check failed", failure);
        System.out.println("UI CHECK PASS: " + System.getProperty("qa.phase"));
    }
    public static class CheckApp extends JavaFxApplication {
        ConfigurableApplicationContext context;
        Parent root;
        TableView<Student> table;
        Stage stage;
        void check(boolean condition, String message) {
            if (!condition) throw new AssertionError(message);
        }
        TextField field(String id) { return (TextField) root.lookup("#" + id); }
        Label label(String id) { return (Label) root.lookup("#" + id); }
        Button button(String text) {
            return root.lookupAll(".button").stream().filter(n -> n instanceof Button b && b.getText().equals(text))
                .map(n -> (Button)n).findFirst().orElseThrow();
        }
        void form(String name, String email, String age) {
            field("nameField").setText(name); field("emailField").setText(email); field("ageField").setText(age);
        }
        void snapshot(Parent target, String name) throws Exception {
            String directory = System.getProperty("qa.screenshots");
            if (directory == null) return;
            target.applyCss(); target.layout();
            WritableImage image = target.snapshot(null, null);
            BufferedImage output = new BufferedImage((int)image.getWidth(), (int)image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int y=0; y<output.getHeight(); y++) for (int x=0; x<output.getWidth(); x++) output.setRGB(x,y,image.getPixelReader().getArgb(x,y));
            Files.createDirectories(Path.of(directory));
            ImageIO.write(output, "png", Path.of(directory, name).toFile());
        }
        void confirm(ButtonType choice, boolean capture) {
            Platform.runLater(() -> {
                try {
                    DialogPane pane = Window.getWindows().stream().filter(w -> w.isShowing() && w != stage)
                        .map(w -> w.getScene().getRoot()).filter(n -> n instanceof DialogPane)
                        .map(n -> (DialogPane)n).findFirst().orElseThrow();
                    if (capture) snapshot(pane, "04-delete-confirm.png");
                    ((Button)pane.lookupButton(choice)).fire();
                } catch (Throwable e) { failure=e; Platform.exit(); }
            });
            button("Delete").fire();
            if (failure != null) throw new AssertionError(failure);
        }
        @Override public void start(Stage stage) throws Exception {
            this.stage=stage;
            var contextField = JavaFxApplication.class.getDeclaredField("context");
            contextField.setAccessible(true);
            context = (ConfigurableApplicationContext)contextField.get(this);
            StudentService service = context.getBean(StudentService.class);
            service.create(new Student("Nguyễn Văn An", "an.nv@fpt.edu.vn",20));
            service.create(new Student("Trần Thị Bình", "binh.tt@fpt.edu.vn",21));
            service.create(new Student("Lê Văn Cường", "cuong.lv@gmail.com",22));
            service.create(new Student("Phạm Thị Dung", "dung.pt@fpt.edu.vn",19));
            service.create(new Student("Hoàng Văn Em", "em.hv@gmail.com",23));
            super.start(stage);
            root=stage.getScene().getRoot();
            table=(TableView<Student>)root.lookup("#tableView");
            Platform.runLater(() -> {
                try { runChecks(); } catch(Throwable e) { failure=e; e.printStackTrace(); }
                finally { stage.close(); Platform.exit(); }
            });
        }
        void runChecks() throws Exception {
            check(table.getItems().size()==5,"TC01 initial students");
            check(label("statusLabel").getText().equals("Loaded 5 students"),"TC01 status");
            check(button("Update").isDisabled() && button("Delete").isDisabled(),"TC01 disabled actions");
            snapshot(root,"01-list.png");
            field("searchField").setText("bình"); field("searchField").fireEvent(new javafx.event.ActionEvent());
            check(table.getItems().size()==1 && table.getItems().getFirst().getName().equals("Trần Thị Bình"),"TC02 Vietnamese search with Enter action");
            check(label("statusLabel").getText().equals("Found 1 students"),"TC02 status");
            field("searchField").setText("GMAIL"); button("Search").fire();
            check(table.getItems().size()==2,"TC03 email ignore case");
            button("Refresh").fire(); table.getSelectionModel().select(1);
            check(field("searchField").getText().isEmpty() && table.getItems().size()==5,"TC04 refresh");
            check(field("nameField").getText().equals("Trần Thị Bình") && !button("Update").isDisabled(),"TC04 selection");
            if (System.getProperty("qa.phase").equals("read")) return;
            button("Clear").fire();
            check(field("nameField").getText().isEmpty() && table.getSelectionModel().isEmpty(),"Clear form and selection");
            form("Võ Thị Giang","giang@fpt.edu.vn","20"); button("Add").fire();
            check(table.getItems().size()==6 && label("messageLabel").getText().equals("Added: Võ Thị Giang"),"TC05 add");
            snapshot(root,"02-add.png");
            form("","valid@x.com","20"); button("Add").fire();
            check(label("messageLabel").getText().equals("Name is required") && field("emailField").getText().equals("valid@x.com"),"TC06 validation preserves input");
            snapshot(root,"03-validation.png");
            form("X","abc","abc"); button("Add").fire();
            check(label("messageLabel").getText().equals("Age must be a number"),"TC07 numeric validation");
            form("X","AN.NV@FPT.EDU.VN","20"); button("Add").fire();
            check(label("messageLabel").getText().equals("Email already exists"),"TC08 duplicate email");
            table.getSelectionModel().select(3); field("ageField").setText("25"); button("Update").fire();
            check(table.getItems().get(3).getAge()==25 && label("messageLabel").getText().equals("Updated: Phạm Thị Dung"),"TC09 update");
            table.getSelectionModel().select(3); field("emailField").setText("abc"); button("Update").fire();
            check(label("messageLabel").getText().equals("Invalid email") && table.getItems().get(3).getEmail().equals("dung.pt@fpt.edu.vn"),"TC10 invalid update leaves table intact");
            table.getSelectionModel().select(4); confirm(ButtonType.CANCEL,true);
            check(table.getItems().size()==6,"TC11 cancel delete");
            confirm(ButtonType.OK,false);
            check(table.getItems().size()==5 && label("messageLabel").getText().equals("Deleted: Hoàng Văn Em"),"TC12 delete");
            stage.setWidth(900); stage.setHeight(500);
            Object pulseKey = new Object();
            javafx.animation.PauseTransition pulse = new javafx.animation.PauseTransition(javafx.util.Duration.millis(300));
            pulse.setOnFinished(event -> Platform.exitNestedEventLoop(pulseKey, null));
            pulse.play(); Platform.enterNestedEventLoop(pulseKey);
            root.applyCss(); root.layout();
            for(String text: new String[]{"Add","Update","Delete","Clear"}) {
                Node node=button(text); var bounds=node.localToScene(node.getBoundsInLocal());
                check(bounds.getMaxX()<=stage.getScene().getWidth()+1 && bounds.getMaxY()<=stage.getScene().getHeight()+1,"minimum window fits " + text);
            }
            context.getBean(org.springframework.jdbc.core.JdbcTemplate.class).execute("DROP TABLE students");
            button("Refresh").fire();
            check(label("messageLabel").getText().startsWith("Cannot load data:") && label("statusLabel").getText().equals("Database error"),"TC13 simulated H2 database failure handled");
        }
        @Override public void stop() {
            super.stop();
            if(context!=null && context.isActive()) failure=new AssertionError("TC14 context still active");
        }
    }
}
