package spring_boot.it211projectfinal.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import spring_boot.it211projectfinal.exeption.BadRequestException;
import spring_boot.it211projectfinal.model.dto.request.EnrollmentRequestDTO;
import spring_boot.it211projectfinal.model.entity.Course;
import spring_boot.it211projectfinal.model.entity.User;
import spring_boot.it211projectfinal.repository.CourseRepository;
import spring_boot.it211projectfinal.repository.EnrollmentRepository;
import spring_boot.it211projectfinal.repository.UserRepository;
import spring_boot.it211projectfinal.service.impl.EnrollmentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Test
    void register_course_duplicate() {

        EnrollmentRequestDTO request =
                EnrollmentRequestDTO.builder()
                        .courseId(1L)
                        .build();

        User user =
                User.builder()
                        .id(1L)
                        .email("student@gmail.com")
                        .build();

        Course course =
                Course.builder()
                        .id(1L)
                        .build();

        when(securityContext.getAuthentication())
                .thenReturn(authentication);

        SecurityContextHolder
                .setContext(securityContext);

        when(authentication.getName())
                .thenReturn("student@gmail.com");

        when(userRepository.findByEmail(
                "student@gmail.com"))
                .thenReturn(Optional.of(user));

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(enrollmentRepository
                .existsByStudentAndCourse(
                        user,
                        course))
                .thenReturn(true);

        assertThrows(
                BadRequestException.class,
                () -> enrollmentService.registerCourse(
                        request)
        );
    }
}
