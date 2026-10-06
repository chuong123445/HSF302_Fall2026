package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.dto.EnrollmentView;
import com.example.fu.de200388.ch4.dto.StudentCreditDTO;
import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.repository.CourseRepository;
import com.example.fu.de200388.ch4.repository.StudentRepository;
import com.example.fu.de200388.ch4.specification.EnrollmentSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    private Student getStudent(String studentCode) {
        if (studentCode == null || studentCode.isBlank()) {
            throw new IllegalArgumentException("Student code must not be blank");
        }
        return studentRepository.findByStudentCode(studentCode.trim())
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentCode));
    }

    private Course getCourse(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Course code must not be blank");
        }
        return courseRepository.findByCode(courseCode.trim())
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseCode));
    }

    @Override
    public List<Course> getCoursesOfStudent(String studentCode) {
        return getStudent(studentCode).getCourses().stream()
                .sorted(Comparator.comparing(Course::getCode))
                .toList();
    }

    @Override
    public List<Student> getStudentsOfCourse(String courseCode) {
        return getCourse(courseCode).getStudents().stream()
                .sorted(Comparator.comparing(Student::getFullName))
                .toList();
    }

    @Override
    public List<Student> findStudentsInCourse(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            return List.of();
        }
        return studentRepository.findByCourses_CodeOrderByFullNameAsc(courseCode);
    }

    @Override
    public long countStudentsInCourse(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            return 0;
        }
        return studentRepository.countByCourses_Code(courseCode.trim());
    }

    @Override
    public List<Student> findActiveStudentsInCourse(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            return List.of();
        }
        return studentRepository.findByCourses_CodeAndActiveTrueOrderByFullNameAsc(courseCode.trim());
    }

    @Override
    public List<Student> findStudentsWithoutCourses() {
        return studentRepository.findByCoursesIsEmptyOrderByFullNameAsc();
    }

    @Override
    public boolean isEnrolled(String studentCode, String courseCode) {
        if (studentCode == null || studentCode.isBlank()
                || courseCode == null || courseCode.isBlank()) {
            return false;
        }
        return studentRepository.existsByStudentCodeAndCourses_Code(
                studentCode.trim(),
                courseCode.trim()
        );
    }

    @Override
    public List<Student> findGoodStudentsInCourse(String courseCode, double minGpa) {
        if (courseCode == null || courseCode.isBlank()) {
            return List.of();
        }
        if (minGpa < 0 || minGpa > 4) {
            throw new IllegalArgumentException("minGpa must be between 0 and 4");
        }
        return studentRepository.findGoodStudentsInCourse(courseCode.trim(), minGpa);
    }

    @Override
    public List<StudentCreditDTO> getCreditSummary(int minCredits) {
        if (minCredits < 0) {
            throw new IllegalArgumentException("minCredits must be greater than or equal to 0");
        }
        return studentRepository.getCreditSummary(minCredits);
    }

    @Override
    public List<Student> findStudentsWithMoreThan(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be greater than or equal to 0");
        }
        return studentRepository.findStudentsWithMoreThanNCourses(n);
    }

    @Override
    public Student getStudentWithCourses(String studentCode) {
        if (studentCode == null || studentCode.isBlank()) {
            throw new IllegalArgumentException("Student code must not be blank");
        }
        return studentRepository.findByStudentCodeWithCourses(studentCode.trim())
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentCode));
    }

    @Override
    public List<EnrollmentView> getEnrollmentsOfDepartment(String deptCode) {
        if (deptCode == null || deptCode.isBlank()) {
            return List.of();
        }
        return studentRepository.findEnrollmentsOfDepartment(deptCode.trim());
    }

    @Override
    public Page<Student> findStudentsInCoursePage(String courseCode, int pageIndex, int size) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("courseCode must not be blank");
        }
        if (pageIndex < 0) {
            throw new IllegalArgumentException("pageIndex must be greater than or equal to 0");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size must be greater than 0");
        }
        return studentRepository.findPageByCourseCode(
                courseCode.trim(),
                PageRequest.of(pageIndex, size, Sort.by("fullName"))
        );
    }

    @Override
    public List<Student> search(String courseCode, String semester, String deptCode, Double minGpa) {
        Specification<Student> specification = Specification.where(EnrollmentSpecs.enrolledIn(courseCode))
                .and(EnrollmentSpecs.inSemester(semester))
                .and(EnrollmentSpecs.inDepartment(deptCode))
                .and(EnrollmentSpecs.gpaAtLeast(minGpa));
        return studentRepository.findAll(specification, Sort.by("fullName"));
    }

    // Bo sung theo guide - TODO 20: transaction ghi va dirty checking owning side.
    @Override
    @Transactional
    public void enroll(String studentCode, String courseCode) {
        Student s = getStudent(studentCode);
        Course c = getCourse(courseCode);
        checkAndEnroll(s, c);
    }

    // TODO 20/22: dung chung quy tac active, khong trung va con cho.
    private void checkAndEnroll(Student s, Course c) {
        if (!s.isActive()) {
            throw new IllegalStateException("Student " + s.getStudentCode() + " is inactive");
        }
        if (s.getCourses().contains(c)) {
            throw new IllegalStateException("Student " + s.getStudentCode()
                    + " already enrolled in " + c.getCode());
        }
        int enrolled = c.getStudents().size();
        if (enrolled >= c.getCapacity()) {
            throw new IllegalStateException("Course " + c.getCode()
                    + " is full (" + enrolled + "/" + c.getCapacity() + ")");
        }
        s.enroll(c);
    }

    // Bo sung theo guide - TODO 21: helper go lien ket o ca hai phia.
    @Override
    @Transactional
    public void unenroll(String studentCode, String courseCode) {
        Student s = getStudent(studentCode);
        Course c = getCourse(courseCode);
        if (!s.getCourses().contains(c)) {
            throw new IllegalStateException("Student " + studentCode + " is not enrolled in " + courseCode);
        }
        s.unenroll(c);
    }

    // Bo sung theo guide - TODO 22: loi dang ky lop moi rollback ca viec go lop cu.
    @Override
    @Transactional
    public void switchCourse(String studentCode, String fromCode, String toCode) {
        if (fromCode == null || fromCode.equals(toCode)) {
            throw new IllegalArgumentException("fromCode and toCode must be different");
        }
        Student s = getStudent(studentCode);
        Course from = getCourse(fromCode);
        Course to = getCourse(toCode);
        if (!s.getCourses().contains(from)) {
            throw new IllegalStateException("Student " + studentCode + " is not enrolled in " + fromCode);
        }
        s.unenroll(from);
        checkAndEnroll(s, to);
    }

    // Bo sung theo guide - TODO 24: native DELETE tren bang trung gian.
    @Override
    @Transactional
    public int removeEnrollmentsOfInactiveStudents() {
        return studentRepository.deleteEnrollmentOfInactiveStudents();
    }
}
