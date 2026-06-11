package spring_boot.it211projectfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot.it211projectfinal.model.entity.Course;
import spring_boot.it211projectfinal.model.entity.Submission;
import spring_boot.it211projectfinal.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    boolean existsByStudentAndCourse(
            User student,
            Course course);

    List<Submission> findByStudent(User student);

    Optional<Submission> findById(Long id);
}
