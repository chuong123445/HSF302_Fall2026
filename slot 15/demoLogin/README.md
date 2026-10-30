# Chapter 08 — Bài 0, B1–B4

Project `demoLogin` thực hiện các bài trong `Chapter8_final.md`, giữ namespace
`fu.de200388.demologin`. Yêu cầu JDK 21; JavaFX 21.0.6 được Maven tải tự động.

## Chạy ứng dụng

Mở terminal tại thư mục này:

```powershell
.\mvnw.cmd javafx:run
```

Màn hình chính có bốn nút mở B1–B4. Mỗi lần mở tạo một form mới.
Có thể chạy trực tiếp class `Application` trong IDE hoặc chọn bài bằng Maven:

```powershell
.\mvnw.cmd javafx:run "-Djavafx.mainClass=fu.de200388.demologin/fu.de200388.demologin.b1.DrinkOrderApp"
.\mvnw.cmd javafx:run "-Djavafx.mainClass=fu.de200388.demologin/fu.de200388.demologin.b2.LoginLayoutApp"
.\mvnw.cmd javafx:run "-Djavafx.mainClass=fu.de200388.demologin/fu.de200388.demologin.b3.RegisterApp"
.\mvnw.cmd javafx:run "-Djavafx.mainClass=fu.de200388.demologin/fu.de200388.demologin.b4.LoginApp"
```

## Nội dung

| Bài | Package | Chức năng |
|---|---|---|
| 0 | `common`, `module-info.java`, `pom.xml` | Maven, module, `AlertUtil`, menu chọn bài |
| B1 | `b1` | Đặt đồ uống, validation, giảm 10% từ 5 ly, log sự kiện, Enter/Esc |
| B2 | `b2` | 5 layout, đăng nhập, ghi nhớ username trong phiên, trang chủ, viền debug |
| B3 | `b3` | Đăng ký, lỗi inline, độ mạnh mật khẩu, đủ 16 tuổi, đăng nhập với tài khoản mới |
| B4 | `b4` | FXML + controller + CSS, hiện mật khẩu, vai trò, khóa sau 3 lần sai, cửa sổ chào mừng |

Không thêm `javafx-web`/`javafx-media` vì các module này chỉ cần cho T5, ngoài phạm vi B1–B4.
Các file HelloController/hello-view.fxml gốc được giữ lại nhưng menu không sử dụng.

## Tài khoản thử

| Bài | Username | Password | Vai trò |
|---|---|---|---|
| B2 | `admin` | `Admin@123` | — |
| B2 | `student` | `Student@123` | — |
| B3 | `admin` | `Admin@123` | — |
| B4 | `student` | `Student@123` | Sinh viên |
| B4 | `teacher` | `Teacher@123` | Giảng viên |

B3 lưu tài khoản trong bộ nhớ đến khi tiến trình kết thúc, dùng mật khẩu nguyên văn theo bài học.
B4 chỉ mô phỏng quên mật khẩu, không gửi email thật. Sau ba lần nhập sai thông tin
tài khoản, mở lại bài B4 để tạo phiên mới. Đăng xuất ở B4 đóng cửa sổ chào mừng.

## Kiểm tra

```powershell
.\mvnw.cmd test
```

JUnit chạy các control JavaFX thật trên FX thread và cần phiên desktop hoạt động:
tính tiền 225.000 đ theo ví dụ đề, lọc số lượng, reset bằng Esc; đăng nhập B2 và ghi nhớ;
validation B3 gồm biên đúng 16 tuổi, đăng ký rồi đăng nhập; load hai FXML,
đồng bộ ô mật khẩu, kiểm tra vai trò, truyền dữ liệu và khóa sau ba lần sai.

FXML ở `src/main/resources/fu/de200388/demologin/b4/` có thể mở trong Scene Builder.
B1–B3 dựng giao diện bằng Java; B4 chuyển cấu trúc sang FXML, style sang CSS,
giữ xử lý sự kiện và đăng nhập trong controller.
