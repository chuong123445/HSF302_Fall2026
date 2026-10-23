package com.hsf302.student_thymeleaf.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO 1: Thêm validation
    //  - studentCode: bắt buộc; regex ^(SE|SS|SA|HE)\d{6}$
    //                 message: "Mã SV phải có dạng SE/SS/SA/HE + 6 chữ số (VD: SE180001)"
    //  - fullName   : bắt buộc; độ dài 2 - 50
    //  - email      : bắt buộc; đúng định dạng email
    @Column(nullable = false, length = 8, unique = true)
    private String studentCode;
    @Column(nullable = false, length = 50)
    @Nationalized
    private String fullName;
    @Column(nullable = false, length = 100)
    private String email;

    // TODO 2: Thêm validation
    //  - dateOfBirth: bắt buộc; phải là ngày trong quá khứ;
    //                 thêm @DateTimeFormat(pattern = "yyyy-MM-dd") để bind với <input type="date">
    //  - gpa        : bắt buộc; 0.0 <= gpa <= 10.0
    //  - major      : bắt buộc (chọn ngành)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @Column(nullable = false)
    private Double gpa;
    @Column(nullable = false, length = 2)
    private String major;

    @Column(nullable = false)
    private boolean active = true;

    public Student() {
    }

    public Student(Long id, String studentCode, String fullName, String email,
                   LocalDate dateOfBirth, Double gpa, String major, boolean active) {
        this.id = id;
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gpa = gpa;
        this.major = major;
        this.active = active;
    }

    public int getAge() {
        return dateOfBirth == null ? 0 : Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public String getRank() {
        if (gpa == null) return "Chưa xếp loại";
        if (gpa >= 9.0) return "Xuất sắc";
        if (gpa >= 8.0) return "Giỏi";
        if (gpa >= 6.5) return "Khá";
        if (gpa >= 5.0) return "Trung bình";
        return "Yếu";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
