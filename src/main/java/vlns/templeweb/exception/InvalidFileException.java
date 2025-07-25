package vlns.templeweb.exception;

import org.springframework.http.HttpStatus;

public class InvalidFileException extends ApiException{
    private static final String ERROR_CODE = "INVALID_FILE";
    private static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public InvalidFileException(String message) {
        super(message, ERROR_CODE, STATUS_CODE);
    }

    public InvalidFileException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, STATUS_CODE);
    }
}
