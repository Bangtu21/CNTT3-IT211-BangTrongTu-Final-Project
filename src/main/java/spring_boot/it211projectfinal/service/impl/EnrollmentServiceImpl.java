package spring_boot.it211projectfinal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring_boot.it211projectfinal.exeption.BadRequestException;
import spring_boot.it211projectfinal.exeption.ResourceNotFoundException;
import spring_boot.it211projectfinal.model.dto.request.EnrollmentRequestDTO;
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
    public void registerCourse(EnrollmentRequestDTO request) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        boolean exists = enrollmentRepository.existsByStudentAndCourse(user, course);

        if (exists) {
            throw new BadRequestException("You have already registered for this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(user)
                .course(course)
                .build();

        enrollmentRepository.save(enrollment);
    }
}
