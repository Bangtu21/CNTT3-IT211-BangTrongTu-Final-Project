package spring_boot.it211projectfinal.exeption;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring_boot.it211projectfinal.model.dto.response.ErrorResponseDTO;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request){

        String message =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        buildError(
                                HttpStatus.BAD_REQUEST,
                                message,
                                request
                        )
                );
    }

    private ErrorResponseDTO buildError(
            HttpStatus status,
            String message,
            HttpServletRequest request){

        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(
            ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        buildError(
                                HttpStatus.NOT_FOUND,
                                ex.getMessage(),
                                request
                        )
                );
    }

    @ExceptionHandler(
            BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        buildError(
                                HttpStatus.BAD_REQUEST,
                                ex.getMessage(),
                                request
                        )
                );
    }

    @ExceptionHandler(
            UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest request){

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        buildError(
                                HttpStatus.UNAUTHORIZED,
                                ex.getMessage(),
                                request
                        )
                );
    }

    @ExceptionHandler(
            ForbiddenException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleForbidden(
            ForbiddenException ex,
            HttpServletRequest request){

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        buildError(
                                HttpStatus.FORBIDDEN,
                                ex.getMessage(),
                                request
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO>
    handleException(
            Exception ex,
            HttpServletRequest request){

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        buildError(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ex.getMessage(),
                                request
                        )
                );
    }
}
