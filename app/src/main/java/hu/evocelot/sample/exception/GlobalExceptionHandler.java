package hu.evocelot.sample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * <p>
 * This class handles exceptions thrown by the application and provides custom
 * responses for both generic and base exceptions. It extends the response with
 * appropriate error details based on the type of exception.
 * </p>
 * 
 * @author mark.danisovszky
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles generic exceptions and creates a response with the exception message.
     * <p>
     * This method is invoked for any uncaught exception in the application. It
     * returns a generic error response with an internal server error (500) status
     * code.
     * </p>
     * 
     * @param ex - the generic exception to handle.
     * @return - {@link ResponseEntity} containing an {@link ErrorResponse} with the
     *         error details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles base exceptions and creates a response with specific exception
     * details.
     * <p>
     * This method is invoked for {@link BaseException} types. It returns a custom
     * error response with the exception's HTTP status and details.
     * </p>
     * 
     * @param ex - the base exception to handle.
     * @return - {@link ResponseEntity} containing an {@link ExceptionResponse} with
     *         the specific error details.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(BaseException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex);

        return new ResponseEntity<>(exceptionResponse, ex.getHttpStatus());
    }
}
