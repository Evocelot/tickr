package hu.evocelot.sample.exception;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;

/**
 * Response class for representing the details of an exception that occurred in
 * the application.
 * <p>
 * This class holds the information to be returned when an exception is thrown,
 * including the HTTP status, the exception message, the type of exception, and
 * the timestamp when the exception occurred.
 * </p>
 * 
 * @author mark.danisovszky
 */
public class ExceptionResponse {

    /**
     * Constructs a new {@code ExceptionResponse} using the provided
     * {@code BaseException}. The exception's HTTP status, message, and type are
     * used to populate the response.
     * 
     * @param exception The exception to extract data from.
     */
    public ExceptionResponse(BaseException exception) {
        this(exception.getHttpStatus(), exception.getMessage(), exception.getExceptionType());
    }

    public ExceptionResponse(HttpStatus httpStatus, String message, ExceptionType exceptionType) {
        setHttpStatus(httpStatus);
        setMessage(message);
        setExceptionType(exceptionType);
        setDateTime(OffsetDateTime.now());
    }

    private HttpStatus httpStatus;

    private String message;

    private ExceptionType exceptionType;

    private OffsetDateTime dateTime;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
