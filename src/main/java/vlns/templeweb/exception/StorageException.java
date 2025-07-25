package vlns.templeweb.exception;

import com.amazonaws.services.s3.model.AmazonS3Exception;

public class StorageException extends RuntimeException {
    public StorageException(String s, AmazonS3Exception e) {
    }
}
