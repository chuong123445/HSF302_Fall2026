package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.repository.CourseRepository;
import com.example.fu.de200388.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentServiceImpl implements EnrollmentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    public Student getStudent(String studentCode){
        if(studentCode==null ||studentCode.isBlank())
            throw new IllegalArgumentException("Student code must not be blank");
        return studentRepository.findByStudentCode(studentCode).
                orElseThrow(() -> new IllegalArgumentException("Student not found:" + studentCode));
    }
    public Course getCourse(String courseCode){
        if(courseCode==null|| courseCode.isBlank())
            throw new IllegalArgumentException("Course code must not be a blank");
        return courseRepository.findByCode(courseCode).
                orElseThrow(()-> new IllegalArgumentException("Course not found: +"+courseCode));

    }
    @Override
    public List<Course> getCoursesOfStudent(String studentCode) {
        return getStudent(studentCode).getCourses().stream().
                sorted((Comparator.comparing(Course::getCode))).toList();
    }

    @Override
    public List<Student> getStudentsOfCourse(String courseCode) {
        return getCourse(courseCode).getStudents().stream().
                sorted(Comparator.comparing(Student::getFullName)).toList();
    }
    @Override
    public List<Student> findStudentsInCourse(String courseCode) {
        return studentRepository.findByCourses_CodeOrderByFullNameAsc(courseCode);
    }

    @Override
    public long countStudentsInCourse(String courseCode) {
        return studentRepository.countByCourses_Code(courseCode);
    }

    @Override
    public List<Student> findActiveStudentsInCourse(String courseCode) {
        return studentRepository.findByCourses_CodeAndActiveTrueOrderByFullNameAsc(courseCode);
    }
    public List<Student> findStudentsWithoutCourses(){
        return studentRepository.findByCoursesIsEmpty();
    }
    public boolean isEnrolled(String studentCode,String courseCode){
                return studentRepository.existsByStudentCodeAndCourses_Code(studentCode,courseCode);
    }
    
}
