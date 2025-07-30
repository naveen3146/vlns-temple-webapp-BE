package vlns.templeweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileMetaData {
    private String fileName;
    private long size;
    private String bucketName;
    private String contentType;
    private String url;

    public FileMetaData(String fileName, long size, String bucketName, String url) {
        this.fileName = fileName;
        this.size = size;
        this.bucketName = bucketName;
        this.url = url;
    }

    public FileMetaData(String fileName, long size, String contentType) {
        this.fileName = fileName;
        this.size = size;
        this.contentType = contentType;
    }

    public FileMetaData(String fileName, long size) {
        this.fileName = fileName;
        this.size = size;
    }
}
