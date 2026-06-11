package spring_boot.it211projectfinal.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring_boot.it211projectfinal.exeption.CloudStorageException;
import spring_boot.it211projectfinal.service.CloudinaryService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(
            MultipartFile file) {

        try {

            Map<?, ?> result =
                    cloudinary.uploader()
                            .upload(
                                    file.getBytes(),
                                    ObjectUtils.emptyMap());

            return result
                    .get("secure_url")
                    .toString();

        } catch (Exception e) {

            throw new CloudStorageException(
                    "Upload file failed");
        }
    }
}
