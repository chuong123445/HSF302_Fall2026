package com.example.fu.de200388.ch4.repository;

import com.example.fu.de200388.ch4.dto.CourseEnrollmentCount;
import com.example.fu.de200388.ch4.dto.CourseStatDTO;
import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
     Optional<Course> findByCode(String code);

     List<Course> findBySemesterOrderByCodeAsc(String semester);

    long countBySemester(String semester);

     List<Course> findByStudents_StudentCodeOrderByCodeAsc(String studentCode);

    List<Course> findDistinctByStudents_Department_CodeOrderByCodeAsc(String departmentCode);

     List<Course> findByStudents_Department_CodeOrderByCodeAsc(String departmentCode);
     List<Course> findByStudentsIsEmpty();
     List<Course> findByCreditsBetween(int minCredit,int maxCredit);
     List<Course> findByCreditsGreaterThan(int credits);
     List<Course> findByNameContainingIgnoreCase(String keyword);

     @Query("""
             SELECT new com.example.fu.de200388.ch4.dto.CourseStatDTO(
                 c.code, c.name, c.capacity, COUNT(s), AVG(s.gpa)
             )
             FROM Course c
             LEFT JOIN c.students s
             GROUP BY c.code, c.name, c.capacity
             ORDER BY c.code
             """)
     List<CourseStatDTO> getCourseStats();

     @Query("""
             SELECT c
             FROM Course c
             WHERE c.capacity <= SIZE(c.students)
             ORDER BY c.code
             """)
     List<Course> findFullCourses();

     @EntityGraph(attributePaths = "students")
     Optional<Course> findWithStudentsByCode(String courseCode);

     @Query(value = """
             SELECT TOP (:n)
                    c.code AS code,
                    c.name AS name,
                    COUNT(sc.student_id) AS enrolled
             FROM courses c
             LEFT JOIN student_courses sc ON sc.course_id = c.id
             GROUP BY c.code, c.name
             ORDER BY enrolled DESC, c.code
             """, nativeQuery = true)
     List<CourseEnrollmentCount> findTopEnrolledNative(@Param("n")int n);

}
