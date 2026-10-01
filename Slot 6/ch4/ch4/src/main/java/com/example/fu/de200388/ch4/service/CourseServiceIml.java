package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceIml implements CourseService {
    private CourseRepository courseRepository;
}
