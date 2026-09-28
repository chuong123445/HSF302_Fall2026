package com.example.fu.de200388.ch4.repository;

import com.example.fu.de200388.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    Optional<Student> findByStudentCode(String studentCode);

    boolean existsByEmail(String email);

    long countByActiveTrue();

    List<Student> findByFullNameContainingIgnoreCase(String fullName);

    List<Student> findByEmailEndingWith(String emailSuffix);

    List<Student> findByEmailIsNull();
}
