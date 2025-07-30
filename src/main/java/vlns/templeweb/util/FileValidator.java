package vlns.templeweb.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vlns.templeweb.model.FileMetaData;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FileValidator {
    private static final Logger logger = LogManager.getLogger(FileValidator.class);
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final Set<String> ALLOWED_CONTENT_TYPES_IMAGES = Set.of(
            // Images
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/bmp",
            "image/webp",
            "image/tiff"
    );

    private static final Set<String> ALLOWED_CONTENT_TYPES_VideoModelS = Set.of(
            // VideoModels
            "VideoModel/mp4",
            "VideoModel/quicktime",
            "VideoModel/x-msVideoModel",
            "VideoModel/x-matroska",
            "VideoModel/webm",
            "VideoModel/mpeg");


    public List<FileMetaData> filterValidFiles(List<MultipartFile> files) {
        Set<String> fileNames = new HashSet<>();
        return files.stream()
                .filter(Objects::nonNull)
                .filter(file -> {
                    if (file.getSize() <= 0) {
                        logger.warn("File '{}' is empty.", file.getOriginalFilename());
                        return false;
                    }
                    if (file.getSize() > MAX_FILE_SIZE) {
                        logger.warn("File '{}' exceeds max size.", file.getOriginalFilename());
                        return false;
                    }
                    if (!ALLOWED_CONTENT_TYPES_IMAGES.contains(file.getContentType())) {
                        logger.warn("File '{}' has invalid content type '{}'.", file.getOriginalFilename(), file.getContentType());
                        return false;
                    }
                    if (!fileNames.add(file.getOriginalFilename())) {
                        logger.warn("Duplicate file name '{}'.", file.getOriginalFilename());
                        return false;
                    }
                    return true;
                })
                .map(file -> new FileMetaData(
                        file.getOriginalFilename(),
                        file.getSize(),
                        file.getContentType()
                ))
                .collect(Collectors.toList());
    }

}