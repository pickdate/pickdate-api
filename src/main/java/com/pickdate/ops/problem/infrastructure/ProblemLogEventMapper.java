package com.pickdate.ops.problem.infrastructure;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.bootstrap.exception.Problem;
import com.pickdate.bootstrap.exception.ProblemCapturedEvent;
import com.pickdate.bootstrap.exception.ProblemDetailsForAdminView;
import com.pickdate.bootstrap.exception.ProblemDetailsWithParameters;
import com.pickdate.ops.problem.domain.ProblemLog;


final class ProblemLogEventMapper {

    private ProblemLogEventMapper() {
    }

    static ProblemLog toEntity(ProblemCapturedEvent problemEvent) {
        var problem = problemEvent.problem();
        var entity = new ProblemLog()
                .withId(Identifier.of(problem.getTraceId()))
                .withStatus(problem.getStatus())
                .withDetail(problem.getDetail())
                .withInstance(problem.getInstance())
                .withStackTrace(problemEvent.stackTrace())
                .withTitle(problem.getTitle());

        if (problem instanceof ProblemDetailsWithParameters params) {
            return entity.withInvalidParams(params.getInvalidParams());
        }

        return entity;
    }

    static Problem toProblem(ProblemLog entity) {
        return ProblemDetailsForAdminView.builder()
                .title(entity.getTitle())
                .status(entity.getStatus())
                .detail(entity.getDetail())
                .instance(entity.getInstance())
                .traceId(entity.getId().getValue())
                .stackTrace(entity.getStackTrace())
                .invalidParams(entity.getInvalidParams())
                .build();
    }
}

