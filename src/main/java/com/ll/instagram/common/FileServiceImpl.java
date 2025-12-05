package com.ll.instagram.common;

import com.ll.instagram.common.exception.BusinessException;
import com.ll.instagram.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");

    @Override
    @Transactional
    public String saveFile(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return null;
            }
            String originalFilename = file.getOriginalFilename();
            String extension = getExtension(originalFilename);

            if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
                throw new BusinessException(ErrorCode.INVALID_FILE_TYPE);
            }

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String savedFileName = UUID.randomUUID() + extension;

            Path filePath = uploadPath.resolve(savedFileName);
            Files.copy(file.getInputStream(), filePath);
            return savedFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getExtension(String filename) {
        if (filename == null || filename.contains(",")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

}
