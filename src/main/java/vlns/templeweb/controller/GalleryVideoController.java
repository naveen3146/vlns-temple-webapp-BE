package vlns.templeweb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vlns.templeweb.dto.Video;

import java.util.List;

@RestController
@RequestMapping("/temple")
@CrossOrigin(origins = "http://localhost:3000")
public class GalleryVideoController {

    @PostMapping("/videos")
    public ResponseEntity<String> uploadVideo(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file) {
        // Save file and metadata
        return ResponseEntity.ok("Video uploaded");
    }

    @GetMapping("/videos")
    public List<Video> getVideos() {
        // return list of videos
        return List.of();
    }

}
