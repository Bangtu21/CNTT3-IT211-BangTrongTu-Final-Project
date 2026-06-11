package spring_boot.it211projectfinal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_boot.it211projectfinal.model.dto.request.LectureMaterialRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.ApiResponseDTO;
import spring_boot.it211projectfinal.model.dto.response.LectureMaterialResponseDTO;
import spring_boot.it211projectfinal.service.LectureMaterialService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lecturer/materials")
@RequiredArgsConstructor
public class LecturerMaterialController {

    private final LectureMaterialService lectureMaterialService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<LectureMaterialResponseDTO>>
    create(
            @RequestBody LectureMaterialRequestDTO request){

        return ResponseEntity.ok(
                ApiResponseDTO.<LectureMaterialResponseDTO>builder()
                        .success(true)
                        .message("Material uploaded successfully")
                        .data(
                                lectureMaterialService.create(
                                        request))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<LectureMaterialResponseDTO>>>
    getAll(){

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<List<LectureMaterialResponseDTO>>
                                builder()
                        .success(true)
                        .message("Get materials successfully")
                        .data(
                                lectureMaterialService.getAll())
                        .build()
        );
    }
}
