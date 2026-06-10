package spring_boot.it211projectfinal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring_boot.it211projectfinal.model.dto.request.CourseRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.CourseResponseDTO;
import spring_boot.it211projectfinal.model.entity.Course;
import spring_boot.it211projectfinal.repository.CourseRepository;
import spring_boot.it211projectfinal.service.CourseService;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public CourseResponseDTO create(CourseRequestDTO request) {
        Course course = Course.builder()
                .courseName(request.getCourseName())
                .description(request.getDescription())
                .build();

        courseRepository.save(course);
        return mapToResponse(course);
    }

    @Override
    public Page<CourseResponseDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return courseRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Page<CourseResponseDTO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return courseRepository.findByCourseNameContainingIgnoreCase(keyword, pageable).map(this::mapToResponse);
    }

    @Override
    public CourseResponseDTO update(Long id, CourseRequestDTO request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());

        courseRepository.save(course);
        return mapToResponse(course);
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    private CourseResponseDTO mapToResponse(Course course){
        return CourseResponseDTO.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .build();
    }
}
