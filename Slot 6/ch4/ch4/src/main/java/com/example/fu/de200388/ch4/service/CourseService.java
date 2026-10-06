package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.dto.CourseEnrollmentCount;
import com.example.fu.de200388.ch4.dto.CourseStatDTO;
import com.example.fu.de200388.ch4.pojo.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    long count();

    List<Course> findAllOrderByCode();

    Optional<Course> findById(Long id);

    Optional<Course> findByCode(String code);

    List<Course> findBySemester(String semester);

    long countBySemester(String semester);

    List<Course> findCoursesOfStudent(String studentCode);

    List<Course> findCoursesOfDepartment(String deptCode, boolean distinct);

    List<Course> findCourseByCreditBetween(int minCredits, int maxCredits);

    List<Course> findCourseByCreditGreaterThan(int credits);

    List<Course> findCourseByKeyword(String keyword);

    List<Course> findCoursesWithoutStudents();

    List<CourseStatDTO> getStatistics();

    List<Course> findFullCourses();

    Course getWithStudents(String code);

    List<CourseEnrollmentCount> findTopEnrolled(int n);

    // Bo sung theo guide - TODO 23: so sanh xoa truc tiep va go lien ket truoc khi xoa.
    void deleteCourseDirectly(String code);

    int deleteCourse(String code);
}
