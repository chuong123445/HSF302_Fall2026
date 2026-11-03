# Kết quả kiểm tra — 2026-11-03

## Build

JDK 21.0.11, Maven 3.9.16; giữ Spring Boot 4.1.1 và JavaFX 21.0.5 trong POM.
`mvn -q clean install` (kèm tham số cache Maven trong workspace) thành công trước
mỗi commit bổ sung. Repository: 10/10; Service: 16/16 lượt chạy; không skip test.
Các test cho sẵn chỉ đổi package/import sang `fu.de200388`.

## Giao diện

Chạy `docs/qa/run-ui-check.ps1` trên desktop Windows với JavaFX thật,
FXML/CSS thật và Spring context thật, dùng H2 in-memory riêng.

| Kịch bản | Kết quả |
|---|---|
| TC01 | 5 sinh viên, Loaded 5 students, Update/Delete bị khóa khi chưa chọn |
| TC02 | Tìm `bình` qua action Enter trả về Trần Thị Bình |
| TC03 | Tìm `GMAIL` trả về 2 email, không phân biệt hoa thường |
| TC04 | Refresh xóa keyword, chọn dòng điền form và bật Update/Delete |
| TC05 | Thêm Võ Thị Giang, danh sách có 6 sinh viên, thông báo thành công |
| TC06 | Tên trống báo Name is required, giữ dữ liệu vừa nhập |
| TC07 | Tuổi abc báo Age must be a number |
| TC08 | Email AN.NV@FPT.EDU.VN bị chặn trùng |
| TC09 | Cập nhật tuổi Phạm Thị Dung thành 25 |
| TC10 | Email abc bị chặn, bảng vẫn giữ email cũ |
| TC11 | Cancel trong hộp xác nhận không xóa sinh viên |
| TC12 | OK xóa Hoàng Văn Em và cập nhật số dòng |
| TC13 (mô phỏng) | Làm mất bảng H2 rồi Refresh: hiện Cannot load data và Database error, app tiếp tục chạy |
| TC14 | Đóng cửa sổ đóng Spring context/Hikari và tiến trình kết thúc |

Đã kiểm tra các nút chính nằm trong cửa sổ ở kích thước tối thiểu 900×500,
và xem trực quan các ảnh giao diện mặc định 1000×600. Bốn ảnh ở `screenshots/`
được chụp tự động từ JavaFX; không phải mockup.

## Giới hạn

Chưa xác minh đăng nhập, tạo bảng và lưu tiếng Việt trên SQL Server thật; script
`seed.sql` được cung cấp để nạp dữ liệu theo đề sau khi cấu hình kết nối.
TC13 mô phỏng lỗi database trên H2, chưa tắt service SQL Server.
Playwright không điều khiển JavaFX desktop; kiểm tra trên bằng API JavaFX.

Phạm vi hoàn thành là các TODO bắt buộc #1–#7; các mục Bonus là bài nâng cao tùy chọn.
Ba commit có sẵn được giữ nguyên, kể cả message commit Repository khác mẫu solution.
