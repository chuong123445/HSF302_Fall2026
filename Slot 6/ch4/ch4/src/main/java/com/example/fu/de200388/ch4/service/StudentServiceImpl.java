package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.pojo.Gender;
import com.example.fu.de200388.ch4.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
