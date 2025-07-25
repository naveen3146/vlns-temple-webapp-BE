package vlns.templeweb.model;

import lombok.Getter;

@Getter
public class ApiResponse<T>{
    // Getters
    private final boolean success;
    private final String message;
    private final T data;
    private final String errorCode;
    private final long timestamp;

    public ApiResponse(boolean success, String message, T data) {
        this(success, message, data, null);
    }

    public ApiResponse(boolean success, String message, T data, String errorCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
    }

}
