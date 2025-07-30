/*
package vlns.templeweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vlns.templeweb.model.FileMetaData;
import vlns.templeweb.service.VideoModelServiceImpl;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/temple")
@CrossOrigin(origins = "http://localhost:3000")
public class GalleryVideoModelController {

    private static final Logger logger = LoggerFactory.getLogger(GalleryVideoModelController.class);

    @Autowired
    VideoModelServiceImpl VideoModelService;

    // Upload VideoModels
    @PostMapping("/VideoModels")
    public ResponseEntity<List<FileMetaData>> uploadVideoModels(@RequestParam("files") MultipartFile[] files) throws IOException {
        logger.info("VideoModels for upload: {}", (Object) files);

        List<MultipartFile> fileList = java.util.Arrays.asList(files);
        List<FileMetaData> metaDataList = VideoModelService.uploadFile(fileList);

        logger.info("meta data for uploaded VideoModels: {}", metaDataList);
        return ResponseEntity.status(201).body(metaDataList);
    }

    // List VideoModels
    @GetMapping("/VideoModels")
    public ResponseEntity<List<FileMetaData>> fetchVideoModels() {
        List<FileMetaData> VideoModels = VideoModelService.listFiles();
        return ResponseEntity.ok(VideoModels);
    }

    // Download VideoModel
    @GetMapping("/VideoModels/{filename}")
    public ResponseEntity<Resource> downloadVideoModel(@PathVariable String filename) {
        Resource fileResource = VideoModelService.downloadFile(filename);
        if (fileResource == null || !fileResource.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                .body(fileResource);
    }

    // Delete VideoModel
    @DeleteMapping("/VideoModels/{filename}")
    public ResponseEntity<Void> deleteVideoModel(@PathVariable String filename) {
        VideoModelService.deleteFile(filename);
        return ResponseEntity.noContent().build();
    }

}
*/
