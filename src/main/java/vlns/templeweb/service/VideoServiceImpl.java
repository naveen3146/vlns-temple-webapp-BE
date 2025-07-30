package vlns.templeweb.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.UrlInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vlns.templeweb.dto.VideoDTO;
import vlns.templeweb.model.VideoModel;
import vlns.templeweb.repository.VideoRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);
    private final AmazonS3Client s3Client;
    private final VideoRepo VideoRepository;
    @Value("${aws.s3.bucket}")
    private String bucket;

    public VideoServiceImpl(AmazonS3Client s3Client, VideoRepo videoRepository) {
        this.s3Client = s3Client;
        this.VideoRepository = videoRepository;
    }

    public VideoDTO uploadVideoModel(MultipartFile file, String title, String description) throws IOException {
        logger.info("request received for uplaod video file {}{}{}", file,title,description);
        if (!Objects.requireNonNull(file.getContentType()).startsWith("video/")) {
            throw new IllegalArgumentException("Invalid file type");
        }
        if (file.getSize() > 100 * 1024 * 1024) {
            throw new IllegalArgumentException("File too large");
        }

        File tempInput = null;
        File tempOutput = null;
        try {
            tempInput = Files.createTempFile("input-", file.getOriginalFilename()).toFile();
            file.transferTo(tempInput);

            // Generate a unique output file name, but do NOT pre-create the file
            String outputFileName = "output-" + UUID.randomUUID() + ".mp4";
            tempOutput = new File(tempInput.getParent(), outputFileName);

            FFmpeg.atPath()
                    .addInput(UrlInput.fromPath(tempInput.toPath()))
                    .addArguments("-vcodec", "libx264")
                    .addArguments("-crf", "23")
                    .addOutput(UrlOutput.toPath(tempOutput.toPath()))
                    .execute();

            String uniqueFileName = "temple-videos/" + UUID.randomUUID() + ".mp4";
            logger.info("generated unique id {}", uniqueFileName);

            // Upload the file directly
            s3Client.putObject(bucket, uniqueFileName, tempOutput);

            String s3Url = "https://" + bucket + ".s3.amazonaws.com/" + uniqueFileName;

            VideoModel videoModel = new VideoModel();
            videoModel.setTitle(title);
            videoModel.setDescription(description);
            videoModel.setS3Url(s3Url);
            videoModel.setFileName(uniqueFileName);
            VideoRepository.save(videoModel);

            // Map entity to DTO
            VideoDTO videoDTO = new VideoDTO();
            videoDTO.setId(videoModel.getId());
            videoDTO.setTitle(videoModel.getTitle());
            videoDTO.setDescription(videoModel.getDescription());
            videoDTO.setS3Url(videoModel.getS3Url());
            videoDTO.setFileName(videoModel.getFileName());
            return videoDTO;
        } catch (Exception e) {
            logger.error("exception occurred during video upload", e);
        } finally {
            // Clean up temporary files
            tempInput.delete();
            tempOutput.delete();
        }

        return null;
    }

    public Resource getVideoResource(String fileName, String rangeHeader) {
        // Download the file from S3 as a Resource
        logger.info("request received for download video file {}{}", fileName, rangeHeader);
        try {
            File tempFile = Files.createTempFile("video-", fileName).toFile();
            s3Client.getObject(bucket, fileName).getObjectContent().transferTo(Files.newOutputStream(tempFile.toPath()));
            org.springframework.core.io.Resource resource = new org.springframework.core.io.FileSystemResource(tempFile);
            return resource;
        } catch (IOException e) {
            logger.error("Error retrieving video resource", e);
            return null;
        }
    }

    // Delete video by ID
    public boolean deleteVideo(Long id) {
        logger.info("request received for delete video file {}", id);
        VideoModel video = VideoRepository.findById(id).orElse(null);
        if (video == null) return false;
        try {
            s3Client.deleteObject(bucket, video.getFileName());
            VideoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting video", e);
            return false;
        }
    }

    // Get all videos
    public List<VideoDTO> getAllVideos() {
        logger.info("request received for fetch all the video files");
        List<VideoModel> videos = VideoRepository.findAll();
        List<VideoDTO> dtos = new ArrayList<>();
        for (VideoModel video : videos) {
            VideoDTO dto = new VideoDTO();
            dto.setId(video.getId());
            dto.setTitle(video.getTitle());
            dto.setDescription(video.getDescription());
            dto.setS3Url(video.getS3Url());
            dto.setFileName(video.getFileName());
            dtos.add(dto);
        }
        return dtos;
    }
}