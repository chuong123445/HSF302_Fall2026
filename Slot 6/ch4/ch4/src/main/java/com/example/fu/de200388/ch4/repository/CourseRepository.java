package com.example.fu.de200388.ch4.repository;

import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;

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

}