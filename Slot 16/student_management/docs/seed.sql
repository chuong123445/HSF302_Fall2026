USE StudentDB;
GO

-- Chạy sau khi đã mở ứng dụng lần đầu để Hibernate tạo bảng students.
-- Chỉ thêm email chưa tồn tại, có thể chạy lại mà không tạo bản ghi trùng.
INSERT INTO students(name, email, age)
SELECT sample.name, sample.email, sample.age
FROM (VALUES
    (N'Nguyễn Văn An', 'an.nv@fpt.edu.vn', 20),
    (N'Trần Thị Bình', 'binh.tt@fpt.edu.vn', 21),
    (N'Lê Văn Cường', 'cuong.lv@gmail.com', 22),
    (N'Phạm Thị Dung', 'dung.pt@fpt.edu.vn', 19),
    (N'Hoàng Văn Em', 'em.hv@gmail.com', 23)
) AS sample(name, email, age)
WHERE NOT EXISTS (
    SELECT 1 FROM students s WHERE LOWER(s.email) = LOWER(sample.email)
);
