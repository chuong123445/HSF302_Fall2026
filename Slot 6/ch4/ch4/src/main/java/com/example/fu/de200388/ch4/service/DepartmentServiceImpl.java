package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.dto.DepartmentStatDTO;
import com.example.fu.de200388.ch4.pojo.Department;
import com.example.fu.de200388.ch4.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public long count() {
        return departmentRepository.count();
    }

    @Override
    public boolean existsById(Long id) {
        return departmentRepository.existsById(id);
    }

    @Override
    public List<Department> findDepartmentsWithoutStudents() {
        return departmentRepository.findByStudentsIsEmpty();
    }

    @Override
    public List<DepartmentStatDTO> getStatistics() {
        return departmentRepository.getStatistics();
    }

    @Override
    public Optional<Department> findByCode(String code) {
        if (code == null || code.isBlank()) {
            return Optional.empty();
        }
        return departmentRepository.findByCode(code.trim());
    }

    @Override
    public Department getWithStudents(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code must not be blank");
        }
        return departmentRepository.findByCodeWithStudents(code.trim())
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + code));
    }
}
