package com.example.fu.de200388.ch4;

import com.example.fu.de200388.ch4.dto.CourseStatDTO;
import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.service.CourseService;
import com.example.fu.de200388.ch4.service.EnrollmentService;
import com.example.fu.de200388.ch4.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Ch4ApplicationTests {

	@Autowired
	private CourseService courseService;

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private StudentService studentService;

	// Bo sung theo guide: kiem tra du lieu sau khi Runner chay het Part E.
	@Test
	void allTodosCompleteWithExpectedPartEState() {
		assertThat(courseService.count()).isEqualTo(5);
		assertThat(studentService.count()).isEqualTo(10);
		assertThat(courseService.findByCode("IAA202")).isEmpty();
		assertThat(courseService.getStatistics().stream().mapToLong(CourseStatDTO::enrolled).sum())
				.isEqualTo(16);

		// TODO 22: doi SWP391 thanh MKT101 thanh cong; PRJ301 duoc giu lai sau rollback.
		assertThat(enrollmentService.getCoursesOfStudent("SE001"))
				.extracting(Course::getCode).containsExactly("HSF302", "MKT101", "PRJ301");
		assertThat(enrollmentService.getStudentsOfCourse("MKT101"))
				.extracting(Student::getStudentCode).containsExactlyInAnyOrder("IA003", "SE001");
		assertThat(enrollmentService.getStudentsOfCourse("AIL303"))
				.extracting(Student::getStudentCode)
				.containsExactlyInAnyOrder("AI001", "AI003", "SE002", "SE004");
		assertThat(enrollmentService.getCoursesOfStudent("AI002")).isEmpty();
		assertThat(enrollmentService.getCoursesOfStudent("IA002"))
				.extracting(Course::getCode).containsExactly("HSF302");

		// TODO 24: chi xoa dang ky, cac sinh vien inactive van ton tai.
		assertThat(enrollmentService.getCoursesOfStudent("SE003")).isEmpty();
		assertThat(enrollmentService.getCoursesOfStudent("IA001")).isEmpty();
		assertThat(enrollmentService.findStudentsWithoutCourses())
				.extracting(Student::getStudentCode).containsExactlyInAnyOrder("IA001", "AI002", "SE003");
	}

}
