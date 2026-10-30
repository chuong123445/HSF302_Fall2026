package fu.de200388.demologin;

import fu.de200388.demologin.b1.DrinkOrderApp;
import fu.de200388.demologin.b2.LoginLayoutApp;
import fu.de200388.demologin.b3.RegisterApp;
import fu.de200388.demologin.b3.UserStore;
import fu.de200388.demologin.b4.LoginApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/** Runs the real controls and FXML on the JavaFX application thread. Needs a desktop session. */
class Chapter8Test {
    @BeforeAll
    static void startToolkit() throws Exception {
        CountDownLatch ready = new CountDownLatch(1);
        Platform.startup(() -> {
            Platform.setImplicitExit(false);
            ready.countDown();
        });
        assertTrue(ready.await(10, TimeUnit.SECONDS));
    }

    @AfterEach
    void closeWindows() throws Exception {
        fx(() -> new ArrayList<>(Window.getWindows()).forEach(Window::hide));
    }

    @AfterAll
    static void stopToolkit() { Platform.exit(); }

    @Test
    void drinkOrderValidatesFiltersDiscountsAndResets() throws Exception {
        fx(() -> {
            DrinkOrderApp app = new DrinkOrderApp();
            Stage stage = new Stage();
            app.start(stage);
            get(app, "btnCalc", Button.class).fire();
            assertTrue(get(app, "lblResult", Label.class).getText().contains("nhập tên"));
            get(app, "tfName", TextField.class).setText("Nguyễn Văn An");
            combo(app, "cbDrink").setValue("Trà sữa");
            get(app, "rbL", RadioButton.class).fire();
            get(app, "chkPearl", CheckBox.class).fire();
            get(app, "chkJelly", CheckBox.class).fire();
            TextField qty = get(app, "tfQty", TextField.class);
            qty.setText("5");
            qty.setText("abc");
            assertEquals("5", qty.getText());
            qty.setText("999");
            assertEquals("5", qty.getText());
            get(app, "btnCalc", Button.class).fire();
            assertTrue(get(app, "lblResult", Label.class).getText().matches("(?s).*225[,.]000.*"));
            qty.setText("0");
            get(app, "btnCalc", Button.class).fire();
            assertEquals("Số lượng từ 1 đến 20", get(app, "lblResult", Label.class).getText());
            qty.setText("21");
            get(app, "btnCalc", Button.class).fire();
            assertEquals("Số lượng từ 1 đến 20", get(app, "lblResult", Label.class).getText());
            Event.fireEvent(stage.getScene(), new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE,
                    false, false, false, false));
            assertEquals("1", qty.getText());
            assertTrue(get(app, "rbM", RadioButton.class).isSelected());
            assertNull(combo(app, "cbDrink").getValue());
        });
    }

    @Test
    void codeLoginChecksCredentialsRemembersUsernameAndChangesSceneRoot() throws Exception {
        fx(() -> {
            LoginLayoutApp app = new LoginLayoutApp();
            Stage stage = new Stage();
            app.start(stage);
            Scene scene = stage.getScene();
            TextField username = scene.getRoot().lookupAll(".text-field").stream()
                    .filter(n -> n instanceof TextField && !(n instanceof PasswordField))
                    .map(TextField.class::cast).findFirst().orElseThrow();
            PasswordField password = (PasswordField) scene.lookup(".password-field");
            username.setText(" ADMIN ");
            password.setText("wrong");
            button(scene.getRoot(), "Đăng nhập").fire();
            assertEquals("", password.getText());
            assertTrue(labels(scene.getRoot(), "Sai username"));
            password.setText("Admin@123");
            scene.getRoot().lookupAll(".check-box").stream().map(CheckBox.class::cast)
                    .filter(c -> c.getText().startsWith("Ghi nhớ")).findFirst().orElseThrow().fire();
            Parent login = scene.getRoot();
            button(login, "Đăng nhập").fire();
            assertSame(scene, stage.getScene());
            assertNotSame(login, scene.getRoot());
            assertTrue(labels(scene.getRoot(), "Xin chào, admin"));
            button(scene.getRoot(), "Đăng xuất").fire();
            assertTrue(scene.getRoot().lookupAll(".text-field").stream()
                    .filter(n -> n instanceof TextField).map(TextField.class::cast)
                    .anyMatch(t -> t.getText().equals("admin")));
        });
    }

    @Test
    void registrationValidatesAllFieldsAndAgeBoundaryThenLogsIn() throws Exception {
        fx(() -> {
            RegisterApp app = new RegisterApp();
            Stage stage = new Stage();
            app.start(stage);
            Button register = get(app, "btnRegister", Button.class);
            assertTrue(register.isDisabled());
            get(app, "chkAgree", CheckBox.class).fire();
            assertFalse(register.isDisabled());
            for (String validator : new String[]{"vFullName", "vEmail", "vUsername", "vPassword", "vConfirm", "vGender", "vDob", "vMajor"}) {
                assertEquals(false, call(app, validator));
            }
            get(app, "tfFullName", TextField.class).setText("An123");
            assertEquals(false, call(app, "vFullName"));
            get(app, "tfFullName", TextField.class).setText("Nguyễn Văn An");
            get(app, "tfEmail", TextField.class).setText("an@example.com");
            get(app, "tfUsername", TextField.class).setText("admin");
            assertEquals(false, call(app, "vUsername"));
            get(app, "tfUsername", TextField.class).setText("chapter8_test");
            get(app, "pfPassword", PasswordField.class).setText("Abcdefg1");
            assertEquals(false, call(app, "vPassword"));
            assertEquals(0.75, get(app, "pbStrength", ProgressBar.class).getProgress());
            get(app, "pfPassword", PasswordField.class).setText("Abcdefg1@");
            get(app, "pfConfirm", PasswordField.class).setText("different");
            assertEquals(false, call(app, "vConfirm"));
            get(app, "pfConfirm", PasswordField.class).setText("Abcdefg1@");
            get(app, "rbMale", RadioButton.class).fire();
            DatePicker dob = get(app, "dpDob", DatePicker.class);
            dob.setValue(LocalDate.now().plusDays(1));
            assertEquals(false, call(app, "vDob"));
            dob.setValue(LocalDate.now().minusYears(16).plusDays(1));
            assertEquals(false, call(app, "vDob"));
            dob.setValue(LocalDate.now().minusYears(16));
            assertEquals(true, call(app, "vDob"));
            combo(app, "cbMajor").setValue("Kỹ thuật phần mềm");
            for (String validator : new String[]{"vFullName", "vEmail", "vUsername", "vPassword", "vConfirm", "vGender", "vDob", "vMajor"}) {
                assertEquals(true, call(app, validator));
            }
            Platform.runLater(Chapter8Test::dismissDialog);
            register.fire();
            assertTrue(UserStore.exists("chapter8_test"));
            assertTrue(UserStore.login(" CHAPTER8_TEST ", "Abcdefg1@").isPresent());
            assertTrue(UserStore.login("chapter8_test", "wrong").isEmpty());
            assertFalse(get(app, "registerView", Parent.class) == stage.getScene().getRoot());
            assertEquals("", get(app, "tfFullName", TextField.class).getText());
            assertTrue(register.isDisabled());
            for (String error : new String[]{"errFullName", "errEmail", "errUsername", "errPassword", "errConfirm", "errGender", "errDob", "errMajor"}) {
                assertEquals("", get(app, error, Label.class).getText());
            }
            TextField user = stage.getScene().getRoot().lookupAll(".text-field").stream()
                    .filter(n -> !(n instanceof PasswordField)).map(TextField.class::cast).findFirst().orElseThrow();
            assertEquals("chapter8_test", user.getText());
            ((PasswordField) stage.getScene().lookup(".password-field")).setText("Abcdefg1@");
            Platform.runLater(Chapter8Test::dismissDialog);
            button(stage.getScene().getRoot(), "Đăng nhập").fire();
        });
    }

    @Test
    void fxmlLoginSynchronizesPasswordChecksRoleAndPassesWelcomeData() throws Exception {
        fx(() -> {
            FXMLLoader loader = new FXMLLoader(LoginApp.class.getResource("login-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Object controller = loader.getController();
            Button login = get(controller, "btnLogin", Button.class);
            assertTrue(login.isDisabled());
            get(controller, "tfUsername", TextField.class).setText("student");
            PasswordField password = get(controller, "pfPassword", PasswordField.class);
            password.setText("Student@123");
            TextField visible = get(controller, "tfPasswordVisible", TextField.class);
            assertEquals(password.getText(), visible.getText());
            get(controller, "chkShow", CheckBox.class).fire();
            assertTrue(visible.isVisible() && visible.isManaged());
            assertFalse(password.isVisible() || password.isManaged());
            visible.setText("Student@123");
            combo(controller, "cbRole").setValue("Giảng viên");
            login.fire();
            assertEquals("Vai trò không đúng với tài khoản", get(controller, "lblError", Label.class).getText());
            combo(controller, "cbRole").setValue("Sinh viên");
            login.fire();
            assertFalse(stage.isShowing());
            Stage welcome = Window.getWindows().stream().filter(Window::isShowing)
                    .filter(w -> w instanceof Stage).map(Stage.class::cast).findFirst().orElseThrow();
            assertTrue(labels(welcome.getScene().getRoot(), "Xin chào student (Sinh viên)!"));
            button(welcome.getScene().getRoot(), "Đăng xuất").fire();
            assertFalse(welcome.isShowing());
        });
    }

    @Test
    void fxmlLoginLocksImmediatelyAfterThirdFailure() throws Exception {
        fx(() -> {
            FXMLLoader loader = new FXMLLoader(LoginApp.class.getResource("login-view.fxml"));
            loader.load();
            Object controller = loader.getController();
            get(controller, "tfUsername", TextField.class).setText("student");
            combo(controller, "cbRole").setValue("Sinh viên");
            for (int i = 0; i < 3; i++) {
                get(controller, "pfPassword", PasswordField.class).setText("wrong");
                get(controller, "btnLogin", Button.class).fire();
            }
            get(controller, "pfPassword", PasswordField.class).setText("Student@123");
            assertTrue(get(controller, "btnLogin", Button.class).isDisabled());
            assertTrue(get(controller, "lblError", Label.class).getText().contains("Sai 3 lần"));
        });
    }

    private static void dismissDialog() {
        for (Window window : new ArrayList<>(Window.getWindows())) {
            if (window.getScene().getRoot() instanceof DialogPane pane) {
                ((Button) pane.lookupButton(ButtonType.OK)).fire();
            }
        }
    }

    private static boolean labels(Parent root, String text) {
        root.applyCss();
        return root.lookupAll(".label").stream().map(Label.class::cast).anyMatch(l -> l.getText().contains(text));
    }

    private static Button button(Parent root, String text) {
        root.applyCss();
        return root.lookupAll(".button").stream().filter(n -> n instanceof Button).map(Button.class::cast)
                .filter(b -> b.getText().equals(text)).findFirst().orElseThrow();
    }

    @SuppressWarnings("unchecked")
    private static ComboBox<String> combo(Object object, String name) throws Exception {
        return get(object, name, ComboBox.class);
    }

    private static <T> T get(Object object, String name, Class<T> type) throws Exception {
        Field field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return type.cast(field.get(object));
    }

    private static Object call(Object object, String name) throws Exception {
        Method method = object.getClass().getDeclaredMethod(name);
        method.setAccessible(true);
        return method.invoke(object);
    }

    private static void fx(CheckedAction action) throws Exception {
        FutureTask<Void> task = new FutureTask<>(() -> { action.run(); return null; });
        Platform.runLater(task);
        task.get(20, TimeUnit.SECONDS);
    }

    @FunctionalInterface
    private interface CheckedAction { void run() throws Exception; }
}
