package vlns.templeweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vlns.templeweb.model.FileMetaData;
import vlns.templeweb.service.PhotoServiceImpl;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/temple")
public class GalleryPhotoController {

    private static final Logger logger = LoggerFactory.getLogger(GalleryPhotoController.class);

    @Autowired
    PhotoServiceImpl photoService;

    @PostMapping("/photos")
    @Operation(summary = "Upload multiple photos", description = "Uploads one or more photos")
    @ApiResponse(responseCode = "201", description = "Photos uploaded successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FileMetaData.class))))
    public ResponseEntity<List<FileMetaData>> uploadImages(@Parameter(
            description = "Files to upload",
            required = true,
            content = @Content(mediaType = "multipart/form-data")
    )
                                                           @RequestParam("files") MultipartFile[] files) throws IOException {
        logger.info("files for upload: {}", (Object) files);

        List<MultipartFile> fileList = java.util.Arrays.asList(files);
        List<FileMetaData> metaDataList = photoService.uploadFile(fileList);

        logger.info("meta data for uploaded files: {}", metaDataList);
        return ResponseEntity.status(201).body(metaDataList);

    }

    @GetMapping("/photos")
    public ResponseEntity<List<FileMetaData>> fetchImages() {
        List<FileMetaData> images = photoService.listFiles();
        return ResponseEntity.ok(images);
    }

    // Download image
    @GetMapping("/photos/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String filename) {
        Resource fileResource = photoService.downloadFile(filename);
        if (fileResource == null || !fileResource.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                .body(fileResource);
         }

    // Delete image
    @DeleteMapping("/photos/{filename}")
    public ResponseEntity<Void> deleteImage(@PathVariable String filename) {
        photoService.deleteFile(filename);
        return ResponseEntity.noContent().build();

        }


}


