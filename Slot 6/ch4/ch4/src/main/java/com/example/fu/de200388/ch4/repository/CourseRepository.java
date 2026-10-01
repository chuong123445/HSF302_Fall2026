package com.example.fu.de200388.ch4.repository;

import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    public Optional<Course>  findByCode(String code);
    public List<Course> findBySemesterOrderByCodeAsc(String semester);
    public long countBySemester(String semester);
}
