package vlns.templeweb.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    /**
     * -- GETTER --
     *  Gets the error code associated with this exception.
     *
     * @return the error code
     */
    private final String errorCode;
    /**
     * -- GETTER --
     *  Gets the HTTP status code associated with this exception.
     *
     * @return the status code
     */
    private final int statusCode;

    /**
     * Constructs a new ApiException with the specified message.
     *
     * @param message the detail message
     */
    public ApiException(String message) {
        super(message);
        this.errorCode = "INTERNAL_ERROR";
        this.statusCode = 500;
    }

    /**
     * Constructs a new ApiException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "INTERNAL_ERROR";
        this.statusCode = 500;
    }

    /**
     * Constructs a new ApiException with all details.
     *
     * @param message the detail message
     * @param errorCode the error code
     * @param statusCode the HTTP status code
     */
    public ApiException(String message, String errorCode, int statusCode) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    /**
     * Constructs a new ApiException with all details and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     * @param errorCode the error code
     * @param statusCode the HTTP status code
     */
    public ApiException(String message, Throwable cause, String errorCode, int statusCode) {
        super(message, cause);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

}
