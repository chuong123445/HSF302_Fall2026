package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.pojo.Gender;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    long count();

    Optional<Student> findById(Long id);

    List<Student> findAllOrderByGpaDesc();

    Page<Student> findPage(int pageIndex, int size, String sortField);

    Optional<Student> findByStudentCode(String code);

    boolean isEmailExisted(String email);

    long countActive();

    List<Student> searchByName(String kw);

    List<Student> findByEmailDomain(String domain);

    List<Student> findWithoutEmail();

    List<Student> findByGpaRange(double min, double max);

    List<Student> findActiveByGender(Gender gender);

    List<Student> findBornAfter(LocalDate date);

    List<Student> findByDepartment(String departmentCode);

    long countByDepartment(String departmentCode);

    List<Student> findTop3ByGpa();

    List<Student> findGoodStudents(String departmentCode, double minGpa);

    List<Student> searchByKeyword(String keyword);
}
