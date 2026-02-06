package com.pickdate.shared.exception;

import java.net.URI;
import java.util.List;

import static com.pickdate.shared.exception.InvalidParamsResolver.resolveDetail;
import static com.pickdate.shared.exception.InvalidParamsResolver.resolveInvalidParams;
import static org.springframework.http.HttpStatus.*;


public final class ProblemFactory {

    private ProblemFactory() {
    }

    public static Problem notFound(NotFound ex, URI uri) {
        return ProblemDetailsWithParameters.builder()
                .title("Resource Not Found")
                .status(NOT_FOUND.value())
                .instance(uri.toString())
                .detail(ex.getDetail())
                .invalidParams(new InvalidParams().withItems(
                        List.of(InvalidParam.builder()
                                .name(ex.getProperty().name())
                                .value(ex.getProperty().value())
                                .reason(ex.getDetail())
                                .build()
                        )))
                .build();
    }

    public static Problem notFound(RuntimeException ex, URI uri) {
        return ProblemDetails.builder()
                .title("Resource Not Found")
                .status(NOT_FOUND.value())
                .instance(uri.toString())
                .detail(ex.getMessage())
                .build();
    }

    public static Problem badRequest(IllegalValue ex, URI uri) {
        return ProblemDetailsWithParameters.builder()
                .title("Validation Error")
                .status(BAD_REQUEST.value())
                .instance(uri.toString())
                .detail(ex.getDetail())
                .invalidParams(new InvalidParams()
                        .withItems(ex.getProperties().stream()
                                .map(property -> new InvalidParam(
                                        property.name(),
                                        property.value(),
                                        ex.getDetail()
                                ))
                                .toList()
                        ))
                .build();
    }

    public static Problem conflict(ResourceAlreadyExist ex, URI uri) {
        return ProblemDetailsWithParameters.builder()
                .title("Resource Conflict")
                .status(CONFLICT.value())
                .instance(uri.toString())
                .detail(ex.getDetail())
                .invalidParams(new InvalidParams().withItems(
                        List.of(InvalidParam.builder()
                                .name(ex.getProperty().name())
                                .value(ex.getProperty().value())
                                .reason(ex.getDetail())
                                .build()
                        )))
                .build();
    }

    public static Problem badRequest(Exception ex, URI uri) {
        var params = resolveInvalidParams(ex);
        var detail = resolveDetail(params);
        return ProblemDetailsWithParameters.builder()
                .title("Validation Error")
                .status(BAD_REQUEST.value())
                .instance(uri.toString())
                .detail(detail)
                .invalidParams(new InvalidParams(params))
                .build();
    }

    public static Problem internalServerError(InternalServerError ex, URI uri) {
        return ProblemDetails.builder()
                .title("Internal Server Error")
                .status(INTERNAL_SERVER_ERROR.value())
                .instance(uri.toString())
                .detail(ex.getDetail())
                .build();
    }

    public static Problem internalServerError(URI uri) {
        return ProblemDetails.builder()
                .title("Internal Server Error")
                .status(INTERNAL_SERVER_ERROR.value())
                .instance(uri.toString())
                .build();
    }
}

