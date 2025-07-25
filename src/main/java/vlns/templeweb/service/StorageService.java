package vlns.templeweb.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import vlns.templeweb.exception.StorageException;
import vlns.templeweb.model.FileMetaData;

import java.util.List;

public interface StorageService {

    List<FileMetaData> uploadFile(List<MultipartFile> files);

    Resource downloadFile(String fileName);

    void deleteFile(String fileName);

    List<FileMetaData> listFiles();

    FileMetaData getFileMetadata(String fileName) throws StorageException;
}
