package spring_boot.it211projectfinal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring_boot.it211projectfinal.exeption.BadRequestException;
import spring_boot.it211projectfinal.exeption.InvalidStateException;
import spring_boot.it211projectfinal.exeption.ResourceNotFoundException;
import spring_boot.it211projectfinal.model.dto.request.GradeRequestDTO;
import spring_boot.it211projectfinal.model.dto.request.SubmissionRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.SubmissionResponseDTO;
import spring_boot.it211projectfinal.model.entity.Course;
import spring_boot.it211projectfinal.model.entity.Submission;
import spring_boot.it211projectfinal.model.entity.User;
import spring_boot.it211projectfinal.model.enums.SubmissionStatus;
import spring_boot.it211projectfinal.repository.CourseRepository;
import spring_boot.it211projectfinal.repository.SubmissionRepository;
import spring_boot.it211projectfinal.repository.UserRepository;
import spring_boot.it211projectfinal.service.CloudinaryService;
import spring_boot.it211projectfinal.service.SubmissionService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final CloudinaryService cloudinaryService;

    @Override
    public SubmissionResponseDTO submit(SubmissionRequestDTO request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if(submissionRepository.existsByStudentAndCourse(student, course)){
            throw new BadRequestException("You already submitted this course");
        }

        Submission submission = Submission
                .builder()
                .githubLink(request.getGithubLink())
                .student(student)
                .course(course)
                .status(SubmissionStatus.SUBMITTED)
                .submittedAt(LocalDateTime.now())
                .build();

        submissionRepository.save(submission);

        return mapToResponse(submission);
    }

    @Override
    public List<SubmissionResponseDTO> mySubmissions() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return submissionRepository
                .findByStudent(student)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SubmissionResponseDTO mapToResponse(Submission submission){

        return SubmissionResponseDTO
                .builder()
                .id(submission.getId())
                .githubLink(submission.getGithubLink())
                .reportUrl(submission.getReportUrl())
                .score(submission.getScore())
                .feedback(submission.getFeedback())
                .status(submission.getStatus())
                .submittedAt(submission.getSubmittedAt())
                .studentId(submission.getStudent().getId())
                .courseId(submission.getCourse().getId())
                .build();
    }

    @Override
    public void gradeSubmission(GradeRequestDTO request) {
        Submission submission = submissionRepository.findById(request.getSubmissionId())
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

        if (submission.getStatus() != SubmissionStatus.SUBMITTED) {
            throw new InvalidStateException("Submission is not in SUBMITTED state");
        }

        if (request.getScore() < 0 || request.getScore() > 100) {
            throw new BadRequestException("Score must be between 0 and 100");
        }

        submission.setScore(request.getScore());

        submission.setFeedback(request.getFeedback());

        submission.setStatus(SubmissionStatus.GRADED);

        submissionRepository.save(submission);
    }

    @Override
    public SubmissionResponseDTO uploadReport(Long submissionId, MultipartFile file) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

        String fileName = file.getOriginalFilename();

        if(fileName == null || (!fileName.endsWith(".pdf")
                        && !fileName.endsWith(".doc")
                        && !fileName.endsWith(".docx"))
        ){
            throw new BadRequestException("Only PDF, DOC, DOCX allowed");
        }

        String url = cloudinaryService.uploadFile(file);

        submission.setReportUrl(url);

        submissionRepository.save(submission);

        return mapToResponse(submission);
    }
}
