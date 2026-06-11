package spring_boot.it211projectfinal.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lecture_materials")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LectureMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String materialUrl;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
