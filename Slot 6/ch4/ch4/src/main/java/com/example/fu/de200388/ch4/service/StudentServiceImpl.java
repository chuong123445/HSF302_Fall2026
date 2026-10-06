package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.dto.StudentSummary;
import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.pojo.Gender;
import com.example.fu.de200388.ch4.repository.StudentRepository;
import com.example.fu.de200388.ch4.specification.StudentSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public long count() {
        return studentRepository.count();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findAllOrderByGpaDesc() {
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC, "gpa"));
    }

    @Override
    public Page<Student> findPage(int pageIndex, int size, String sortField) {
        if (pageIndex < 0) {
            throw new IllegalArgumentException("pageIndex must be greater than or equal to 0");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size must be greater than 0");
        }
        if (sortField == null || sortField.isBlank()) {
            throw new IllegalArgumentException("sortField must not be blank");
        }

        PageRequest pageRequest = PageRequest.of(
                pageIndex,
                size,
                Sort.by(Sort.Direction.ASC, sortField)
        );
        return studentRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Student> findByStudentCode(String code) {
        if (code == null || code.isBlank()) {
            return Optional.empty();
        }
        return studentRepository.findByStudentCode(code.trim());
    }

    @Override
    public boolean isEmailExisted(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return studentRepository.existsByEmail(email.trim());
    }

    @Override
    public long countActive() {
        return studentRepository.countByActiveTrue();
    }

    @Override
    public List<Student> searchByName(String kw) {
        if (kw == null || kw.isBlank()) {
            return List.of();
        }
        return studentRepository.findByFullNameContainingIgnoreCase(kw.trim());
    }

    @Override
    public List<Student> findByEmailDomain(String domain) {
        if (domain == null || domain.isBlank()) {
            return List.of();
        }

        String normalizedDomain = domain.trim();
        if (!normalizedDomain.startsWith("@")) {
            normalizedDomain = "@" + normalizedDomain;
        }
        return studentRepository.findByEmailEndingWith(normalizedDomain);
    }

    @Override
    public List<Student> findWithoutEmail() {
        return studentRepository.findByEmailIsNull();
    }

    @Override
    public List<Student> findByGpaRange(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be less than or equal to max");
        }
        return studentRepository.findByGpaBetweenOrderByGpaDesc(min, max);
    }

    @Override
    public List<Student> findActiveByGender(Gender gender) {
        if (gender == null) {
            return List.of();
        }
        return studentRepository.findByGenderAndActiveTrue(gender);
    }

    @Override
    public List<Student> findBornAfter(LocalDate date) {
        if (date == null) {
            return List.of();
        }
        return studentRepository.findByDobAfter(date);
    }

    @Override
    public List<Student> findByDepartment(String departmentCode) {
        if (departmentCode == null || departmentCode.isBlank()) {
            return List.of();
        }
        return studentRepository.findByDepartment_CodeOrderByFullNameAsc(departmentCode.trim());
    }

    @Override
    public long countByDepartment(String departmentCode) {
        if (departmentCode == null || departmentCode.isBlank()) {
            return 0;
        }
        return studentRepository.countByDepartment_Code(departmentCode.trim());
    }

    @Override
    public List<Student> findTop3ByGpa() {
        return studentRepository.findTop3ByOrderByGpaDesc();
    }

    @Override
    public List<Student> findGoodStudents(String departmentCode, double minGpa) {
        if (departmentCode == null || departmentCode.isBlank()) {
            return List.of();
        }
        return studentRepository.findGoodStudents(departmentCode.trim(), minGpa);
    }

    @Override
    public List<Student> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        return studentRepository.searchByKeyword(keyword.trim());
    }

    @Override
    public List<Student> findAboveAverageGpa() {
        return studentRepository.findAboveAverageGpa();
    }

    @Override
    public List<Student> findTopNInDepartment(String departmentCode, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        if (departmentCode == null || departmentCode.isBlank()) {
            return List.of();
        }
        return studentRepository.findTopNInDepartment(departmentCode.trim(), n);
    }

    @Override
    public List<StudentSummary> getActiveSummaries() {
        return studentRepository.getActiveSummaries();
    }

    @Override
    public Page<Student> findActiveByDepartment(String departmentCode, int pageIndex, int size) {
        if (departmentCode == null || departmentCode.isBlank()) {
            throw new IllegalArgumentException("departmentCode must not be blank");
        }
        if (pageIndex < 0) {
            throw new IllegalArgumentException("pageIndex must be greater than or equal to 0");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size must be greater than 0");
        }
        return studentRepository.findActiveByDepartment(
                departmentCode.trim(),
                PageRequest.of(pageIndex, size)
        );
    }

    @Override
    public List<Student> search(
            String keyword,
            String departmentCode,
            Double minimumGpa,
            Boolean active
    ) {
        Specification<Student> specification = StudentSpecs.nameContains(keyword)
                .and(StudentSpecs.inDepartment(departmentCode))
                .and(StudentSpecs.gpaAtLeast(minimumGpa))
                .and(StudentSpecs.isActive(active));
        return studentRepository.findAll(
                specification,
                Sort.by(Sort.Direction.ASC, "fullName")
        );
    }

    @Override
    @Transactional
    public Student updateGpa(String studentCode, double newGpa) {
        if (studentCode == null || studentCode.isBlank()) {
            throw new IllegalArgumentException("studentCode must not be blank");
        }
        if (newGpa < 0 || newGpa > 4) {
            throw new IllegalArgumentException("GPA must be between 0 and 4");
        }

        Student student = studentRepository.findByStudentCode(studentCode.trim())
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentCode));
        student.setGpa  (newGpa);
        return student;
    }

    @Override
    @Transactional
    public int deactivateLowGpa(double threshold) {
        return studentRepository.deactivateLowGpa(threshold);
    }

    @Override
    @Transactional
    public long deleteInactiveStudents() {
        return studentRepository.deleteByActiveFalse();
    }
}
