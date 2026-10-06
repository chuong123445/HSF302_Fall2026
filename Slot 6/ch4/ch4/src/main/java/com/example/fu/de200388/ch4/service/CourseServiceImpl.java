package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.dto.CourseEnrollmentCount;
import com.example.fu.de200388.ch4.dto.CourseStatDTO;
import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public long count() {
        return courseRepository.count();
    }

    @Override
    public List<Course> findAllOrderByCode() {
        return courseRepository.findAll(Sort.by("code"));
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Optional<Course> findByCode(String code) {
        if (code == null || code.isBlank()) {
            return Optional.empty();
        }
        return courseRepository.findByCode(code.trim());
    }

    @Override
    public List<Course> findBySemester(String semester) {
        if (semester == null || semester.isBlank()) {
            return List.of();
        }
        return courseRepository.findBySemesterOrderByCodeAsc(semester.trim());
    }

    @Override
    public long countBySemester(String semester) {
        if (semester == null || semester.isBlank()) {
            return 0;
        }
        return courseRepository.countBySemester(semester.trim());
    }

    @Override
    public List<Course> findCoursesOfStudent(String studentCode) {
        if (studentCode == null || studentCode.isBlank()) {
            return List.of();
        }
        return courseRepository.findByStudents_StudentCodeOrderByCodeAsc(studentCode.trim());
    }

    @Override
    public List<Course> findCoursesOfDepartment(String deptCode, boolean distinct) {
        if (deptCode == null || deptCode.isBlank()) {
            return List.of();
        }
        String normalizedDeptCode = deptCode.trim();
        return distinct
                ? courseRepository.findDistinctByStudents_Department_CodeOrderByCodeAsc(normalizedDeptCode)
                : courseRepository.findByStudents_Department_CodeOrderByCodeAsc(normalizedDeptCode);
    }

    @Override
    public List<Course> findCourseByCreditBetween(int minCredits, int maxCredits) {
        if (minCredits > maxCredits) {
            throw new IllegalArgumentException("minCredits must be less than or equal to maxCredits");
        }
        return courseRepository.findByCreditsBetween(minCredits, maxCredits);
    }

    @Override
    public List<Course> findCourseByCreditGreaterThan(int credits) {
        return courseRepository.findByCreditsGreaterThan(credits);
    }

    @Override
    public List<Course> findCourseByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        return courseRepository.findByNameContainingIgnoreCase(keyword.trim());
    }

    @Override
    public List<Course> findCoursesWithoutStudents() {
        return courseRepository.findByStudentsIsEmpty();
    }

    @Override
    public List<CourseStatDTO> getStatistics() {
        return courseRepository.getCourseStats();
    }

    @Override
    public List<Course> findFullCourses() {
        return courseRepository.findFullCourses();
    }

    @Override
    public Course getWithStudents(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code must not be blank");
        }
        return courseRepository.findWithStudentsByCode(code.trim())
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + code));
    }

    @Override
    public List<CourseEnrollmentCount> findTopEnrolled(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        return courseRepository.findTopEnrolledNative(n);
    }

    // Bo sung theo guide - TODO 23a: co y gay loi FK de Runner quan sat va bat loi.
    @Override
    @Transactional
    public void deleteCourseDirectly(String code) {
        Course c = getCourse(code);
        courseRepository.delete(c);
        courseRepository.flush();
    }

    // Bo sung theo guide - TODO 23b: go owning side truoc khi xoa course.
    @Override
    @Transactional
    public int deleteCourse(String code) {
        Course c = getCourse(code);
        // Copy collection vi unenroll() cung thay doi c.getStudents().
        Set<Student> students = new HashSet<>(c.getStudents());
        students.forEach(s -> s.unenroll(c));
        courseRepository.delete(c);
        return students.size();
    }

    private Course getCourse(String code) {
        return findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + code));
    }
}
