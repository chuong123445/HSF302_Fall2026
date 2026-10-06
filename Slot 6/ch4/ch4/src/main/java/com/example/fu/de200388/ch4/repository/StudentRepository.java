package com.example.fu.de200388.ch4.repository;

import com.example.fu.de200388.ch4.dto.EnrollmentView;
import com.example.fu.de200388.ch4.dto.StudentCreditDTO;
import com.example.fu.de200388.ch4.dto.StudentSummary;
import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Department;
import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.pojo.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

//    Optional<Student> findByStudentCode(String studentCode);
//
//    boolean existsByEmail(String email);
//
//    long countByActiveTrue();
//
//    List<Student> findByFullNameContainingIgnoreCase(String fullName);
//
//    List<Student> findByEmailEndingWith(String emailSuffix);
//
//    List<Student> findByEmailIsNull();
//
//    List<Student> findByGpaBetweenOrderByGpaDesc(double minGpa, double maxGpa);
//
//    List<Student> findByGenderAndActiveTrue(Gender gender);
//
//    List<Student> findByDobAfter(LocalDate dob);
//
//    List<Student> findByDepartment_CodeOrderByFullNameAsc(String departmentCode);
//
//    long countByDepartment_Code(String departmentCode);
//
//    List<Student> findTop3ByOrderByGpaDesc();
//@Query("""
//            SELECT s
//            FROM Student s
//            WHERE s.department.code = :deptCode
//              AND s.gpa >= :minGpa
//            ORDER BY s.gpa DESC
//            """)
//List<Student> findGoodStudents(
//        @Param("deptCode") String departmentCode,
//        @Param("minGpa") double minGpa
//);
//
//    @Query("""
//            SELECT s
//            FROM Student s
//            WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
//               OR LOWER(COALESCE(s.email, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
//            ORDER BY s.fullName
//            """)
//    List<Student> searchByKeyword(@Param("keyword") String keyword);
//
//    @Query("""
//            SELECT s
//            FROM Student s
//            WHERE s.gpa > (SELECT AVG(allStudents.gpa) FROM Student allStudents)
//            ORDER BY s.gpa DESC
//            """)
//    List<Student> findAboveAverageGpa();
//
//    @Query(value = """
//            SELECT TOP (:limit) s.*
//            FROM students s
//            JOIN departments d ON d.id = s.department_id
//            WHERE d.code = :deptCode
//            ORDER BY s.gpa DESC
//            """, nativeQuery = true)
//    List<Student> findTopNInDepartment(
//            @Param("deptCode") String departmentCode,
//            @Param("limit") int limit
//    );
//
//    @Query("""
//            SELECT s.studentCode AS studentCode,
//                   s.fullName AS fullName,
//                   s.gpa AS gpa,
//                   s.department.name AS departmentName
//            FROM Student s
//            WHERE s.active = true
//            ORDER BY s.fullName
//            """)
//    List<StudentSummary> getActiveSummaries();
//
//    @Query("""
//            SELECT s
//            FROM Student s
//            WHERE s.active = true
//              AND s.department.code = :deptCode
//            ORDER BY s.gpa DESC
//            """)
//    Page<Student> findActiveByDepartment(
//            @Param("deptCode") String departmentCode,
//            Pageable pageable
//    );
//
//    @Modifying(clearAutomatically = true)
//    @Query("""
//            UPDATE Student s
//            SET s.active = false
//            WHERE s.active = true
//              AND s.gpa < :threshold
//            """)
//    int deactivateLowGpa(@Param("threshold") double threshold);
//
//    @Modifying(clearAutomatically = true)
//    @Query("""
//            UPDATE Student s
//            SET s.department = :targetDepartment
//            WHERE s.department = :sourceDepartment
//            """)
//    int transferStudents(
//            @Param("sourceDepartment") Department sourceDepartment,
//            @Param("targetDepartment") Department targetDepartment
//    );
//
//    long deleteByActiveFalse();
    //ex1
    Optional<Student> findByStudentCode(String StudentCode);
    boolean existsByEmail(String email);
    long countByActiveTrue();
    List<Student> findByFullNameContainingIgnoreCase(String keyword);
    List<Student> findByEmailEndingWith(String keyword);
    List<Student> findByEmailIsNull();
    List<Student> findByGpaBetweenOrderByGpaDesc(double minGpa,double maxGpa);
    List<Student> findByGenderAndActiveTrue(Gender gender);
    List<Student> findByDobAfter(LocalDate dob);
    List<Student> findByDepartment_CodeOrderByFullNameAsc(String departmentCode);
    long countByDepartment_Code(String departmentCode);
    List<Student> findTop3ByOrderByGpaDesc();
    List<Student> findByDepartmentCodeAndGpaGreaterThan(String departmentCode,double minGpa);
    @Query(value = """
                       Select s
                       From Student s join s.department d
                       Where d.code = :departmentCode and s.gpa > :gpa """)
    List<Student> findGoodStudents(@Param(value = "departmentCode") String departmentCode,
                                   @Param(value = "gpa") double gpa);
    @Query(value = """
            Select s
            From Student s
            Where Lower(s.fullName) Like Lower(CONCAT('%', :keyword,'%'))  
                 Or Lower(COALESCE(s.email, '')) Like Lower(CONCAT('%',:keyword,'%')) 
             """)
    List<Student> searchByKeyword(@Param(value = "keyword") String keyword);

    @Query("""
            Select s
            From Student s
            Where s.gpa > (Select avg(allStudent.gpa) From Student allStudent)
            Order By s.gpa DESC
            """)
    List<Student> findAboveAverageGpa();

    @Query(value = """
            Select Top (:limit) s.*
            From students s join departments d on s.department_id = d.id
            Where d.Code = :departmentCode
            Order By s.gpa Desc""",nativeQuery = true)
    List<Student> findTopNInDepartment(@Param(value="departmentCode") String departmentCode,@Param(value = "limit") int limit);
    @Query(value= """
            Select s.studentCode as studentCode,
                   s.fullName as fullName,
                   s.gpa as gpa,
                   d.name as departmentName
            From Student s inner join s.department d
            Where s.active=true
             Order By fullName""")
    List<StudentSummary> getActiveSummaries();
    @Query(value= """
            Select s
            From Student s
            Where s.active= true and s.department.code= :departmentCode
            Order By s.gpa DESC
            """)
    Page<Student>  findActiveByDepartment(@Param(value = "departmentCode") String departmentCode,Pageable pageable);
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value = """
            Update Student s
            Set s.active = false
            Where s.active = true and s.gpa < :threshold
            """)
    int deactivateLowGpa(@Param("threshold") double threshold);
    @Modifying(flushAutomatically = true,clearAutomatically = true)
            @Query(value = """
                    Update Student s
                    Set s.department= :newDepartment
                    Where s.department.code = :oldDepartmentCode""")
    int transferStudents(@Param("oldDepartmentCode") String oldDepartmentCode,
                         @Param("newDepartment") Department newDepartment) ;


    int deleteByActiveFalse();
    //ex2
    List<Student> findByCourses_CodeOrderByFullNameAsc(String courseCode);
    long countByCourses_Code(String courseName);
    List<Student> findByCourses_CodeAndActiveTrueOrderByFullNameAsc(String courseCode);
    List<Student> findByCoursesIsEmpty();
    List<Student> findByCoursesIsEmptyOrderByFullNameAsc();
    boolean existsByStudentCodeAndCourses_Code(String studentCode,String courseCode);
    @Query(value= """
            Select s
            From Student s join s.courses c
            Where c.code= :courseCode and s.gpa>= :minGpa
            Order By s.gpa Desc""")
    List<Student> findGoodStudentsInCourse(@Param("courseCode") String courseCode,
                                           @Param("minGpa") double minGpa);
    @Query(value = """
            Select new com.example.fu.de200388.ch4.dto.StudentCreditDTO(
                s.studentCode, s.fullName, COUNT(c), SUM(c.credits)
            )
            From Student s left join s.courses c
            Group By s.studentCode,s.fullName
            Having SUM(c.credits) >= :minCredits
            Order By SUM(c.credits) DESC, s.fullName
            """)
    List<StudentCreditDTO> getCreditSummary(@Param("minCredits") long minCredits);
    @Query(value= """
            Select s
            From Student s 
            Where Size(s.courses)>:numberOfCourses
            Order By s.fullName""")
    List<Student> findStudentsWithMoreThanNCourses(@Param("numberOfCourses") int numberOfCourses);
    @Query(value = """
            Select s
            From Student s left join fetch  s.courses 
            Where s.studentCode= :studentCode
            """)
    Optional<Student> findByStudentCodeWithCourses(@Param("studentCode") String studentCode);
    @Query("""
            Select s.studentCode as studentCode,
                   s.fullName as fullName,
                   c.code as courseCode,
                   c.name as courseName,
                   c.credits as credits
            From Student s join s.department d join s.courses c
            Where d.code=:departmentCode
            Order By s.studentCode, c.code
            """)
    List<EnrollmentView> findEnrollmentsOfDepartment(@Param("departmentCode")String departmentCode);
    @Query(
            value = """
                    Select s
                    From Student s join s.courses c
                    Where c.code = :courseCode
                    """,
            countQuery = """
                    Select count(s)
                    From Student s join s.courses c
                    Where c.code = :courseCode
                    """
    )
    Page<Student> findPageByCourseCode(@Param("courseCode") String courseCode, Pageable pageable);
    // Bo sung theo guide - TODO 24: native SQL, flush truoc va clear cache sau bulk DELETE.
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value = """
            DELETE FROM student_courses
            WHERE student_id IN (SELECT id FROM students WHERE active = 0)
            """,nativeQuery = true)
    int deleteEnrollmentOfInactiveStudents();
}
