package com.pickdate.iam;

import com.pickdate.shared.exception.Problem;
import com.pickdate.shared.exception.ProblemDetailsForAdminView;
import com.pickdate.shared.exception.ProblemDetailsWithParameters;
import com.pickdate.shared.model.Identifier;


public final class ProblemEventMapper {

    private ProblemEventMapper() {
    }

    public static ProblemEntity toEntity(Problem problem, String stackTrace) {
        var entity = new ProblemEntity()
                .withId(Identifier.of(problem.getTraceId()))
                .withStatus(problem.getStatus())
                .withDetail(problem.getDetail())
                .withInstance(problem.getInstance())
                .withStackTrace(stackTrace)
                .withTitle(problem.getTitle());

        if (problem instanceof ProblemDetailsWithParameters params) {
            return entity.withInvalidParams(params.getInvalidParams());
        }

        return entity;
    }

    public static Problem toProblem(ProblemEntity entity) {
        return ProblemDetailsForAdminView.builder()
                .title(entity.getTitle())
                .status(entity.getStatus())
                .detail(entity.getDetail())
                .instance(entity.getInstance())
                .traceId(entity.getId().value())
                .stackTrace(entity.getStackTrace())
                .invalidParams(entity.getInvalidParams())
                .build();
    }
}

