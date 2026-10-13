USE ThymeleafDemo;
GO

IF OBJECT_ID(N'dbo.khoa_hoc', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.khoa_hoc (
        id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        ten_khoa_hoc NVARCHAR(100) NOT NULL,
        giang_vien NVARCHAR(100) NULL,
        so_tin_chi INT NULL
    );
END;
GO

INSERT INTO dbo.khoa_hoc (ten_khoa_hoc, giang_vien, so_tin_chi)
SELECT sample.ten_khoa_hoc, sample.giang_vien, sample.so_tin_chi
FROM (VALUES
    (N'Lập trình Java', N'Nguyễn Văn A', 3),
    (N'Spring Boot cơ bản', N'Trần Thị B', 3),
    (N'Cơ sở dữ liệu', N'Lê Văn C', 4)
) AS sample(ten_khoa_hoc, giang_vien, so_tin_chi)
WHERE NOT EXISTS (
    SELECT 1 FROM dbo.khoa_hoc AS existing
    WHERE existing.ten_khoa_hoc = sample.ten_khoa_hoc
      AND existing.giang_vien = sample.giang_vien
);
GO
