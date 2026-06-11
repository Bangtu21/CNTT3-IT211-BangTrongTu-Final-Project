package spring_boot.it211projectfinal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot.it211projectfinal.model.dto.request.GradeRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.ApiResponseDTO;
import spring_boot.it211projectfinal.service.SubmissionService;

@RestController
@RequestMapping("/api/v1/lecturer/grades")
@RequiredArgsConstructor
public class LecturerGradeController {
    private final SubmissionService submissionService;

    @PutMapping
    public ResponseEntity<ApiResponseDTO<Void>>
    gradeSubmission(
            @RequestBody GradeRequestDTO request){

        submissionService.gradeSubmission(
                request);

        return ResponseEntity.ok(
                ApiResponseDTO.<Void>builder()
                        .success(true)
                        .message("Graded successfully")
                        .build()
        );
    }
}
