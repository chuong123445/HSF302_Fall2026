package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.pojo.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService  {
    long count();
    List<Course> findAllOrderByCode();
    Optional<Course> findById(Long id);
    Optional<Course> findByCode(String code);
    List<Course> findBySemester(String semester);
    long countBySemester(String semester);
    List<Course> findCoursesOfStudent(String studentCode);
    List<Course> findCoursesOfDepartment(String deptCode, boolean distinct);
    List<Course> findCourseByCreditBetween(int minCredits,int maxCredits);
    List<Course> findCourseByCreditGreaterThan(int credits);
    List<Course> findCourseByKeyword(String keyword);
    List<Course> findCoursesWithoutStudents();
}
