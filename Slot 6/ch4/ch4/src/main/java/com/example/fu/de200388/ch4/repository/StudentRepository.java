package com.example.fu.de200388.ch4.repository;

import com.example.fu.de200388.ch4.dto.StudentSummary;
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

    @Query("""
            SELECT s
            FROM Student s
            WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(COALESCE(s.email, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
            ORDER BY s.fullName
            """)
    List<Student> searchByKeyword(@Param("keyword") String keyword);

    @Query("""
            SELECT s
            FROM Student s
            WHERE s.gpa > (SELECT AVG(allStudents.gpa) FROM Student allStudents)
            ORDER BY s.gpa DESC
            """)
    List<Student> findAboveAverageGpa();

    @Query(value = """
            SELECT TOP (:limit) s.*
            FROM students s
            JOIN departments d ON d.id = s.department_id
            WHERE d.code = :deptCode
            ORDER BY s.gpa DESC
            """, nativeQuery = true)
    List<Student> findTopNInDepartment(
            @Param("deptCode") String departmentCode,
            @Param("limit") int limit
    );

    @Query("""
            SELECT s.studentCode AS studentCode,
                   s.fullName AS fullName,
                   s.gpa AS gpa,
                   s.department.name AS departmentName
            FROM Student s
            WHERE s.active = true
            ORDER BY s.fullName
            """)
    List<StudentSummary> getActiveSummaries();
}
