package com.example.fu.de200388.ch4.repository;

import com.example.fu.de200388.ch4.dto.DepartmentStatDTO;
import com.example.fu.de200388.ch4.pojo.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByStudentsIsEmpty();

    @Query("""
            SELECT new com.example.fu.de200388.ch4.dto.DepartmentStatDTO(
                d.code, d.name, COUNT(s), AVG(s.gpa)
            )
            FROM Department d
            LEFT JOIN d.students s
            GROUP BY d.code, d.name
            ORDER BY d.code
            """)
    List<DepartmentStatDTO> getStatistics();

    Optional<Department> findByCode(String code);

    @Query("""
            SELECT DISTINCT d
            FROM Department d
            LEFT JOIN FETCH d.students
            WHERE d.code = :code
            """)
    Optional<Department> findDepartmentByCodeWithStudents(@Param("code") String code);

    @Query(value = "Select d.id, d.code, d.name, count(s.id) " +
            "From departments d left join students s on s.department_id= d.id " +
            "Group by d.id, d.name,d.code " +
            "Having count(s.id) > 3 ",nativeQuery = true)
    List<Department> findAllDepartmentHasMoreThan3Student();
}
