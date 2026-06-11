package spring_boot.it211projectfinal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_boot.it211projectfinal.model.dto.request.EnrollmentRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.ApiResponseDTO;
import spring_boot.it211projectfinal.service.EnrollmentService;

@RestController
@RequestMapping("/api/v1/student/enrollments")
@RequiredArgsConstructor
public class StudentEnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<Void>>
    registerCourse(
            @RequestBody EnrollmentRequestDTO request){

        enrollmentService.registerCourse(request);

        return ResponseEntity.ok(
                ApiResponseDTO.<Void>builder()
                        .success(true)
                        .message("Registered successfully")
                        .build()
        );
    }
}
