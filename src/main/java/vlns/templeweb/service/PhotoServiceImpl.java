package vlns.templeweb.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vlns.templeweb.exception.StorageException;
import vlns.templeweb.model.FileMetaData;
import vlns.templeweb.util.FileValidator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements StorageService {
    private static final Logger logger = LogManager.getLogger(PhotoServiceImpl.class);


    private final AmazonS3 s3Client;
    private final String bucketName;
    private final FileValidator fileValidator;

    public PhotoServiceImpl(AmazonS3 s3Client, @Value("${aws.s3.bucket}") String bucketName, FileValidator fileValidator) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.fileValidator = fileValidator;
    }

    @Override
    public List<FileMetaData> uploadFile(List<MultipartFile> files) {
        if (files.size() > 10) {
            throw new StorageException("Cannot upload more than 10 files at once", null);
        }
        //FileValidator.filterValidFiles(files);
        logger.info("Uploading {} files", files.size());
        List<FileMetaData> metaDataList = new ArrayList<>();
        for (MultipartFile file : files) {
            try (InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(bucketName, file.getOriginalFilename(), inputStream, null);
                String fileUrl = s3Client.getUrl(bucketName, file.getOriginalFilename()).toString();
                metaDataList.add(new FileMetaData(file.getOriginalFilename(), file.getSize(), bucketName,fileUrl));
            } catch (Exception e) {
                logger.error("Error uploading file {} to S3: ", file.getOriginalFilename(), e);
                throw new StorageException("Failed to upload file: " + file.getOriginalFilename(), e instanceof AmazonS3Exception ? (AmazonS3Exception) e : null);
            }
        }
        return metaDataList;
    }

    @Override
    public Resource downloadFile(String fileName) {
        try {
            InputStream inputStream = s3Client.getObject(bucketName, fileName).getObjectContent();
            return new InputStreamResource(inputStream);
        } catch (Exception e) {
            logger.error("Error downloading file from S3: ", e);
            throw new StorageException("Failed to download file", (AmazonS3Exception) e);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            s3Client.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            logger.error("Error deleting file from S3: ", e);
            throw new StorageException("Failed to delete file", (AmazonS3Exception) e);
        }
    }

    @Override
    public List<FileMetaData> listFiles() {
        try {
            List<S3ObjectSummary> summaries = s3Client.listObjects(bucketName).getObjectSummaries();
            if (summaries == null || summaries.isEmpty()) {
                logger.info("No files found in bucket: {}", bucketName);
                return Collections.emptyList();
            }
            return summaries.stream()
                    .map(obj -> {
                        String url = s3Client.getUrl(bucketName, obj.getKey()).toString();
                        return new FileMetaData(obj.getKey(), obj.getSize(), bucketName, url);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error listing files from S3: ", e);
            throw new StorageException("Failed to list files", e instanceof AmazonS3Exception ? (AmazonS3Exception) e : null);
        }
    }
    @Override
    public FileMetaData getFileMetadata(String fileName) throws StorageException {
        try {
            ObjectMetadata metadata = s3Client.getObjectMetadata(bucketName, fileName);
            return new FileMetaData(
                    fileName,
                    metadata.getContentLength(),
                    bucketName,""
            );
        } catch (AmazonS3Exception e) {
            if (e.getStatusCode() == 404) {
                logger.warn("File not found: {}", fileName);
                return null;  // Return null for non-existent files
            }
            logger.error("Error getting metadata for file: {}", fileName, e);
            throw new StorageException("Failed to get file metadata from S3", e);
        } catch (Exception e) {
            logger.error("Unexpected error getting metadata for file: {}", fileName, e);
            throw new StorageException("Unexpected error getting file metadata", (AmazonS3Exception) e);
        }
    }

}
