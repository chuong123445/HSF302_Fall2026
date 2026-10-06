package com.example.fu.de200388.ch4.specification;

import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Student;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public final class EnrollmentSpecs {

    private EnrollmentSpecs() {
    }

    public static Specification<Student> enrolledIn(String courseCode) {
        return (root, query, criteriaBuilder) -> {
            if (courseCode == null || courseCode.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            query.distinct(true);
            Join<Student, Course> course = root.join("courses");
            return criteriaBuilder.equal(course.get("code"), courseCode.trim());
        };
    }

    public static Specification<Student> inSemester(String semester) {
        return (root, query, criteriaBuilder) -> {
            if (semester == null || semester.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            query.distinct(true);
            Join<Student, Course> course = root.join("courses");
            return criteriaBuilder.equal(course.get("semester"), semester.trim());
        };
    }

    public static Specification<Student> inDepartment(String deptCode) {
        return (root, query, criteriaBuilder) -> {
            if (deptCode == null || deptCode.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("department").get("code"), deptCode.trim());
        };
    }

    public static Specification<Student> gpaAtLeast(Double minGpa) {
        return (root, query, criteriaBuilder) -> minGpa == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.greaterThanOrEqualTo(root.get("gpa"), minGpa);
    }
}
