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

    private static final Set<String> ALLOWED_CONTENT_TYPES_ViDEOS = Set.of(
            // Videos
            "video/mp4",
            "video/quicktime",
            "video/x-msvideo",
            "video/x-matroska",
            "video/webm",
            "video/mpeg");

   /* public static List<FileMetaData> filterValidFiles(List<MultipartFile> files) {
*//*        Set<String> fileNames = new HashSet<>();
        return files.stream()
                .filter(Objects::nonNull)
                .filter(file -> file.getSize() > 0)
                .filter(file -> file.getSize() <= MAX_FILE_SIZE)
                .filter(file -> ALLOWED_CONTENT_TYPES_IMAGES.contains(file.getContentType()))
                .filter(file -> fileNames.add(file.getOriginalFilename()))
                .map(file -> new FileMetaData(
                        file.getOriginalFilename(),
                        file.getSize(),
                        file.getContentType()
                ))
                .collect(Collectors.toList());
    }*/

}