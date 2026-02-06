package com.pickdate.iam;

import com.pickdate.shared.exception.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

import static com.pickdate.shared.exception.ProblemFactory.*;


@Slf4j
@RestControllerAdvice
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
class ExceptionAdvice {

    private ProblemService problemService;

    @ApiResponse(responseCode = "404", description = "Not found")
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Problem> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        log.info("Resource not found: {}", ex.getMessage());
        URI uri = getUri(request);
        Problem problem = notFound(ex, uri);
        problemService.save(ProblemEventMapper.toEntity(problem, ex.getMessage()));
        return new ResponseEntity<>(problem, HttpStatusCode.valueOf(problem.getStatus()));
    }

    @ApiResponse(responseCode = "404", description = "Not found")
    @ExceptionHandler(NotFound.class)
    ResponseEntity<Problem> handleNotFound(NotFound ex, HttpServletRequest request) {
        log.info("Resource not found: {}", ex.getMessage());
        URI uri = getUri(request);
        Problem problem = notFound(ex, uri);
        problemService.save(ProblemEventMapper.toEntity(problem, ex.getMessage()));
        return new ResponseEntity<>(problem, HttpStatusCode.valueOf(problem.getStatus()));
    }

    @ApiResponse(responseCode = "400", description = "Validation exception")
    @ExceptionHandler(IllegalValue.class)
    ResponseEntity<Problem> handleBadRequest(IllegalValue ex, HttpServletRequest request) {
        log.warn("Validation exception: {}", ex.toString());
        URI uri = getUri(request);
        Problem problem = badRequest(ex, uri);
        problemService.save(ProblemEventMapper.toEntity(problem, ex.getMessage()));
        return ResponseEntity.status(HttpStatusCode.valueOf(problem.getStatus())).body(problem);
    }

    @ApiResponse(responseCode = "400", description = "Validation exception")
    @ExceptionHandler({MethodArgumentNotValidException.class, MissingRequestHeaderException.class, IllegalArgumentException.class})
    ResponseEntity<Problem> handleBadRequest(Exception ex, HttpServletRequest request) {
        log.warn("Validation exception, {}", ex.getMessage());
        URI uri = getUri(request);
        Problem problem = badRequest(ex, uri);
        problemService.save(ProblemEventMapper.toEntity(problem, ex.getMessage()));
        return ResponseEntity.status(HttpStatusCode.valueOf(problem.getStatus())).body(problem);
    }

    @ApiResponse(responseCode = "409", description = "Resource already exists")
    @ExceptionHandler(ResourceAlreadyExist.class)
    ResponseEntity<Problem> handleConflict(ResourceAlreadyExist ex, HttpServletRequest request) {
        log.warn("Resource already exists, {}", ex.getMessage());
        URI uri = getUri(request);
        Problem problem = conflict(ex, uri);
        problemService.save(ProblemEventMapper.toEntity(problem, ex.getMessage()));
        return ResponseEntity.status(HttpStatusCode.valueOf(problem.getStatus())).body(problem);
    }

    @ApiResponse(responseCode = "500", description = "Server exception")
    @ExceptionHandler({InternalServerError.class})
    ResponseEntity<Problem> handleInternalServerException(InternalServerError ex, HttpServletRequest request) {
        log.error("Internal server error: {}", ex.getMessage(), ex);
        URI uri = getUri(request);
        Problem problem = internalServerError(ex, uri);
        problemService.save(ProblemEventMapper.toEntity(problem, ex.getMessage()));
        return new ResponseEntity<>(problem, HttpStatusCode.valueOf(problem.getStatus()));
    }

    @ApiResponse(responseCode = "500", description = "Server exception")
    @ExceptionHandler(Exception.class)
    ResponseEntity<Problem> handleInternalServerException(Exception ex, HttpServletRequest request) {
        log.error("Internal server error: {}", ex.getMessage(), ex);
        URI uri = getUri(request);
        Problem problem = internalServerError(uri);
        problemService.save(ProblemEventMapper.toEntity(problem, ex.getMessage()));
        return new ResponseEntity<>(problem, HttpStatusCode.valueOf(problem.getStatus()));
    }

    private URI getUri(HttpServletRequest request) {
        return URI.create(request.getRequestURL().toString());
    }
}
