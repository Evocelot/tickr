package hu.evocelot.sample.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception class for handling application-specific exceptions.
 * <p>
 * This class extends {@link Exception} and provides additional properties such
 * as HTTP status and exception type to categorize and handle exceptions in the
 * application.
 * </p>
 * 
 * @author mark.danisovszky
 */
public class BaseException extends Exception {

    /**
     * Constructs a new {@code BaseException} with the specified HTTP status,
     * exception type, and detailed message.
     * 
     * @param httpStatus    The HTTP status to associate with this exception.
     * @param exceptionType The type of exception.
     * @param message       The detail message for the exception.
     */
    public BaseException(HttpStatus httpStatus, ExceptionType exceptionType, String message) {
        super(message);
        setHttpStatus(httpStatus);
        setExceptionType(exceptionType);
    }

    private HttpStatus HttpStatus;

    private ExceptionType exceptionType;

    public HttpStatus getHttpStatus() {
        return HttpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        HttpStatus = httpStatus;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }
}
