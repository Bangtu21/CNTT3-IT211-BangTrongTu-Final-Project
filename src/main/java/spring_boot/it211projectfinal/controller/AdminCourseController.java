package spring_boot.it211projectfinal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_boot.it211projectfinal.model.dto.request.CourseRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.CourseResponseDTO;
import spring_boot.it211projectfinal.service.CourseService;

@RestController
@RequestMapping("/api/v1/admin/courses")
@RequiredArgsConstructor
public class AdminCourseController {
    private final CourseService courseService;

    @PostMapping
    public CourseResponseDTO create(@RequestBody CourseRequestDTO request){
        return courseService.create(request);
    }

    @GetMapping
    public Page<CourseResponseDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return courseService.getAll(page,size);
    }

    @GetMapping("/search")
    public Page<CourseResponseDTO> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return courseService.search(keyword, page, size);
    }

    @PutMapping("/{id}")
    public CourseResponseDTO update(
            @PathVariable Long id,
            @RequestBody CourseRequestDTO request
    ){
        return courseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
