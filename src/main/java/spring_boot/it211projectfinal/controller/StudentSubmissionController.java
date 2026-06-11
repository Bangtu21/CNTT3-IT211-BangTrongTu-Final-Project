package spring_boot.it211projectfinal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring_boot.it211projectfinal.model.dto.request.SubmissionRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.ApiResponseDTO;
import spring_boot.it211projectfinal.model.dto.response.SubmissionResponseDTO;
import spring_boot.it211projectfinal.service.SubmissionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student/submissions")
@RequiredArgsConstructor
public class StudentSubmissionController {
    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<SubmissionResponseDTO>>
    submit(
            @RequestBody SubmissionRequestDTO request){

        return ResponseEntity.ok(
                ApiResponseDTO.<SubmissionResponseDTO>builder()
                        .success(true)
                        .message("Submission created successfully")
                        .data(
                                submissionService.submit(
                                        request))
                        .build()
        );
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponseDTO<List<SubmissionResponseDTO>>>
    mySubmissions(){

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<List<SubmissionResponseDTO>>
                                builder()
                        .success(true)
                        .message("Get submissions successfully")
                        .data(
                                submissionService.mySubmissions())
                        .build()
        );
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponseDTO<SubmissionResponseDTO>>
    uploadReport(

            @RequestParam Long submissionId,

            @RequestParam MultipartFile file){

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<SubmissionResponseDTO>builder()
                        .success(true)
                        .message("Upload report successfully")
                        .data(
                                submissionService
                                        .uploadReport(
                                                submissionId,
                                                file))
                        .build()
        );
    }
}
