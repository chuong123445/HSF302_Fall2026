package com.example.fu.de200388.ch4.repository;

import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.pojo.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    Optional<Student> findByStudentCode(String studentCode);

    boolean existsByEmail(String email);

    long countByActiveTrue();

    List<Student> findByFullNameContainingIgnoreCase(String fullName);

    List<Student> findByEmailEndingWith(String emailSuffix);

    List<Student> findByEmailIsNull();

    List<Student> findByGpaBetweenOrderByGpaDesc(double minGpa, double maxGpa);

    List<Student> findByGenderAndActiveTrue(Gender gender);

    List<Student> findByDobAfter(LocalDate dob);

    List<Student> findByDepartment_CodeOrderByFullNameAsc(String departmentCode);

    long countByDepartment_Code(String departmentCode);

    List<Student> findTop3ByOrderByGpaDesc();

    @Query("""
            SELECT s
            FROM Student s
            WHERE s.department.code = :deptCode
              AND s.gpa >= :minGpa
            ORDER BY s.gpa DESC
            """)
    List<Student> findGoodStudents(
            @Param("deptCode") String departmentCode,
            @Param("minGpa") double minGpa
    );
}
