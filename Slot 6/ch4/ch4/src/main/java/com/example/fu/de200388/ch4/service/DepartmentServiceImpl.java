package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.dto.DepartmentStatDTO;
import com.example.fu.de200388.ch4.pojo.Department;
import com.example.fu.de200388.ch4.repository.DepartmentRepository;
import com.example.fu.de200388.ch4.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;

    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository,
            StudentRepository studentRepository
    ) {
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
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

    @Override
    @Transactional
    public int transferStudentsAndDelete(String sourceCode, String targetCode) {
        if (sourceCode == null || sourceCode.isBlank()
                || targetCode == null || targetCode.isBlank()) {
            throw new IllegalArgumentException("Department codes must not be blank");
        }

        String normalizedSourceCode = sourceCode.trim();
        String normalizedTargetCode = targetCode.trim();
        if (normalizedSourceCode.equals(normalizedTargetCode)) {
            throw new IllegalArgumentException("Source and target departments must be different");
        }

        Department source = departmentRepository.findByCode(normalizedSourceCode)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Department not found: " + normalizedSourceCode
                ));
        Department target = departmentRepository.findByCode(normalizedTargetCode)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Department not found: " + normalizedTargetCode
                ));

        int transferredStudents = studentRepository.transferStudents(source, target);
        departmentRepository.deleteById(source.getId());
        return transferredStudents;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
