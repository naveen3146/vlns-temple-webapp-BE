package vlns.templeweb.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vlns.templeweb.dto.VideoDTO;
import vlns.templeweb.service.VideoServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    private final VideoServiceImpl VideoService;

    public VideoController(VideoServiceImpl VideoService) {
        this.VideoService = VideoService;
    }

    @PostMapping
    public ResponseEntity<VideoDTO> uploadVideoModel(
            @RequestParam("file") MultipartFile file,
            @RequestParam String title,
            @RequestParam String description) throws Exception {
        VideoDTO videoDTO = VideoService.uploadVideoModel(file, title, description);
        return ResponseEntity.ok(videoDTO);
    }



    @GetMapping("/videos/{fileName}")
    public ResponseEntity<Resource> streamVideo(@PathVariable String fileName, @RequestHeader(value = "Range", required = false) String rangeHeader) {
        Resource videoResource = VideoService.getVideoResource(fileName, rangeHeader);
        if (videoResource == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header("Content-Type", "video/mp4")
                .header("Accept-Ranges", "bytes")
                .body(videoResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        boolean deleted = VideoService.deleteVideo(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<VideoDTO>> getAllVideos() {
        List<VideoDTO> videos = VideoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }
}
