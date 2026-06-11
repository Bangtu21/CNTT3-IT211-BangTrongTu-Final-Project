package spring_boot.it211projectfinal.service;

import org.springframework.web.multipart.MultipartFile;
import spring_boot.it211projectfinal.model.dto.request.GradeRequestDTO;
import spring_boot.it211projectfinal.model.dto.request.SubmissionRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.SubmissionResponseDTO;

import java.util.List;

public interface SubmissionService {
    SubmissionResponseDTO submit(
            SubmissionRequestDTO request);

    List<SubmissionResponseDTO> mySubmissions();

    void gradeSubmission(GradeRequestDTO request);

    SubmissionResponseDTO uploadReport(
            Long submissionId,
            MultipartFile file);
}
