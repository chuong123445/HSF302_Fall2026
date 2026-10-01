package com.example.fu.de200388.ch4.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code", nullable = false, unique = true, length = 10)
    private String studentCode;

    @Column(name = "full_name", nullable = false, length = 100, columnDefinition = "nvarchar(100)")
    private String fullName;

    @Column(unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private double gpa;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    @ManyToMany
    @JoinTable(name = "student_courses",
    joinColumns = @JoinColumn(name="student_id"),
    inverseJoinColumns = @JoinColumn(name="course_id"))
    private Set<Course> courses= new HashSet<>();
    public void enroll(Course c){
        courses.add(c);
        c.getStudents().add(this);
    }
    public void unenroll(Course c){
        courses.remove(c);
        c.getStudents().remove(this);
    }
    public boolean equals(Object o){
        if(this==o) return true;
        if( !(o instanceof Student other)) return false;
        return studentCode != null && other.getStudentCode().equals(getStudentCode());
    }
    public int hashCode(){
        return Objects.hashCode(studentCode);
    }
    protected Student() {
    }

    public Student(
            String studentCode,
            String fullName,
            String email,
            Gender gender,
            LocalDate dob,
            double gpa,
            boolean active
    ) {
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.gpa = gpa;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentCode='" + studentCode + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", dob=" + dob +
                ", gpa=" + gpa +
                ", active=" + active +
                '}';
    }
}
