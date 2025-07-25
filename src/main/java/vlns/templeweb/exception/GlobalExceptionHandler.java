/*
package vlns.templeweb.exception;


import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import vlns.templeweb.model.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException ex) {
        if (ex instanceof InvalidFileException) {
            logger.warn("Invalid file: {}", ex.getMessage());
        } else {
            logger.error("API error: {}", ex.getMessage(), ex);
        }

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null,
                        ex.getErrorCode()
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        logger.warn("Validation error: {}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new ApiResponse<>(
                        false,
                        "Validation failed: " + ex.getMessage(),
                        null,
                        "VALIDATION_ERROR"
                ));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex) {
        logger.warn("File size exceeded: {}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new ApiResponse<>(
                        false,
                        "File size exceeds the maximum allowed limit",
                        null,
                        "FILE_TOO_LARGE"
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        logger.error("Unexpected error: ", ex);
        return ResponseEntity
                .internalServerError()
                .body(new ApiResponse<>(
                        false,
                        "An unexpected error occurred",
                        null,
                        "INTERNAL_ERROR"
                ));
    }
}
*/
