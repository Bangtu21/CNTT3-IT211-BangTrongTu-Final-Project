package spring_boot.it211projectfinal.model.entity;

import jakarta.persistence.*;
import lombok.*;
import spring_boot.it211projectfinal.model.enums.SubmissionStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String githubLink;

    private String reportUrl;

    private Double score;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
