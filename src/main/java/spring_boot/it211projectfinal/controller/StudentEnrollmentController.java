package spring_boot.it211projectfinal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot.it211projectfinal.service.EnrollmentService;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentEnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/courses/{courseId}/register")
    public String registerCourse(@PathVariable Long courseId){
        Long studentId = 1L;
        enrollmentService.registerCourse(studentId, courseId);
        return "Registered successfully";
    }
}
