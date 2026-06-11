package spring_boot.it211projectfinal.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadFile(
            MultipartFile file);
}
