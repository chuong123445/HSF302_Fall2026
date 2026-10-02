package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceIml implements CourseService {
    private final CourseRepository courseRepository;
    public long count(){
        return courseRepository.count();
    }
    public List<Course> findAllOrderByCode(){
        return courseRepository.findAll(Sort.by("code"));
    }
    public Optional<Course> findById(Long id){
        return courseRepository.findById(id);
    }
    @Override
    public Optional<Course> findByCode(String code) {
        return courseRepository.findByCode(code);
    }

    @Override
    public List<Course> findBySemester(String semester) {
        return courseRepository.findBySemesterOrderByCodeAsc(semester);
    }

    @Override
    public long countBySemester(String semester) {
        return courseRepository.countBySemester(semester);
    }@Override
    public List<Course> findCoursesOfStudent(String studentCode) {
        return courseRepository.findByStudents_StudentCodeOrderByCodeAsc(studentCode);
    }

    @Override
    public List<Course> findCoursesOfDepartment(String deptCode, boolean distinct) {
        return distinct
                ? courseRepository.findDistinctByStudents_Department_CodeOrderByCodeAsc(deptCode)
                : courseRepository.findByStudents_Department_CodeOrderByCodeAsc(deptCode);
    }
    public List<Course> findCourseByCreditBetween(int minCredits,int maxCredits){
        return courseRepository.findByCreditsBetween(minCredits,maxCredits);
    }
    public List<Course> findCourseByCreditGreaterThan(int credits){
        return courseRepository.findByCreditsGreaterThan(credits);
    }
    public List<Course> findCourseByKeyword(String keyword){
        return courseRepository.findByNameContainingIgnoreCase(keyword);
    }
    public List<Course> findCoursesWithoutStudents(){
        return courseRepository.findByStudentsIsEmpty();
    }


}
