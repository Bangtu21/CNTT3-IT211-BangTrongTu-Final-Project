package spring_boot.it211projectfinal.service;

import spring_boot.it211projectfinal.model.dto.request.EnrollmentRequestDTO;

public interface EnrollmentService {
    void registerCourse(EnrollmentRequestDTO request);
}
