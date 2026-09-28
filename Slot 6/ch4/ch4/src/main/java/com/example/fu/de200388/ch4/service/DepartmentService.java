package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.dto.DepartmentStatDTO;
import com.example.fu.de200388.ch4.pojo.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    long count();

    boolean existsById(Long id);

    List<Department> findDepartmentsWithoutStudents();

    List<DepartmentStatDTO> getStatistics();

    Optional<Department> findByCode(String code);

    Department getWithStudents(String code);

    int transferStudentsAndDelete(String sourceCode, String targetCode);

    List<Department> findAll();
}
