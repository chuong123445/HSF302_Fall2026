package com.example.fu.de200388.ch4.specification;

import com.example.fu.de200388.ch4.pojo.Student;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public final class StudentSpecs {

    private StudentSpecs() {
    }

    public static Specification<Student> nameContains(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            String pattern = "%" + keyword.trim().toLowerCase(Locale.ROOT) + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), pattern);
        };
    }

    public static Specification<Student> inDepartment(String departmentCode) {
        return (root, query, criteriaBuilder) -> {
            if (departmentCode == null || departmentCode.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("department").get("code"), departmentCode.trim());
        };
    }

    public static Specification<Student> gpaAtLeast(Double minimumGpa) {
        return (root, query, criteriaBuilder) -> minimumGpa == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.greaterThanOrEqualTo(root.get("gpa"), minimumGpa);
    }

    public static Specification<Student> isActive(Boolean active) {
        return (root, query, criteriaBuilder) -> active == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("active"), active);
    }
}
