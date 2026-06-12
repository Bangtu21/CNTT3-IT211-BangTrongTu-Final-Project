package spring_boot.it211projectfinal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_boot.it211projectfinal.exeption.ResourceNotFoundException;
import spring_boot.it211projectfinal.model.dto.request.LectureMaterialRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.LectureMaterialResponseDTO;
import spring_boot.it211projectfinal.model.entity.Course;
import spring_boot.it211projectfinal.model.entity.LectureMaterial;
import spring_boot.it211projectfinal.repository.CourseRepository;
import spring_boot.it211projectfinal.repository.LectureMaterialRepository;
import spring_boot.it211projectfinal.service.LectureMaterialService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureMaterialServiceImpl implements LectureMaterialService {
    private final LectureMaterialRepository lectureMaterialRepository;

    private final CourseRepository courseRepository;

    @Override
    public LectureMaterialResponseDTO create(LectureMaterialRequestDTO request) {

        Course course = courseRepository.findById(request.getCourseId())
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        LectureMaterial material = LectureMaterial.builder()
                .title(request.getTitle())
                .materialUrl(request.getMaterialUrl())
                .course(course)
                .build();

        lectureMaterialRepository.save(material);

        return mapToResponse(material);
    }

    @Override
    public List<LectureMaterialResponseDTO> getAll() {
        return lectureMaterialRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private LectureMaterialResponseDTO mapToResponse(LectureMaterial material) {
        return LectureMaterialResponseDTO
                .builder()
                .id(material.getId())
                .title(material.getTitle())
                .materialUrl(material.getMaterialUrl())
                .courseId(material.getCourse().getId())
                .build();
    }
}
