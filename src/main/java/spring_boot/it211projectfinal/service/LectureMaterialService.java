package spring_boot.it211projectfinal.service;

import spring_boot.it211projectfinal.model.dto.request.LectureMaterialRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.LectureMaterialResponseDTO;

import java.util.List;

public interface LectureMaterialService {
    LectureMaterialResponseDTO create(
            LectureMaterialRequestDTO request);

    List<LectureMaterialResponseDTO> getAll();
}
