package spring_boot.it211projectfinal.service;

import org.springframework.data.domain.Page;
import spring_boot.it211projectfinal.model.dto.request.CourseRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.CourseResponseDTO;

public interface CourseService {
    CourseResponseDTO create(CourseRequestDTO request);

    Page<CourseResponseDTO> getAll(int page, int size);

    Page<CourseResponseDTO> search(String keyword, int page, int size);

    CourseResponseDTO update(Long id, CourseRequestDTO request);

    void delete(Long id);
}
