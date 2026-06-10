package spring_boot.it211projectfinal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_boot.it211projectfinal.model.entity.Course;
import spring_boot.it211projectfinal.model.entity.Enrollment;
import spring_boot.it211projectfinal.model.entity.User;
import spring_boot.it211projectfinal.repository.CourseRepository;
import spring_boot.it211projectfinal.repository.EnrollmentRepository;
import spring_boot.it211projectfinal.repository.UserRepository;
import spring_boot.it211projectfinal.service.EnrollmentService;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public void registerCourse(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow();

        Course course = courseRepository.findById(courseId)
                .orElseThrow();

        if(enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Already registered");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .build();

        enrollmentRepository.save(enrollment);
    }
}
