package com.pickdate.ops.problem.infrastructure;

import com.pickdate.bootstrap.exception.ProblemCapturedEvent;
import com.pickdate.ops.problem.application.ProblemLogUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
class ProblemLogListener {

    private final ProblemLogUseCase problemLogUseCase;

    @ApplicationModuleListener
    public void handleProblemEvent(ProblemCapturedEvent problemEvent) {
        log.info("Handling problem event: {}", problemEvent);
        var entity = ProblemLogEventMapper.toEntity(problemEvent);
        problemLogUseCase.save(entity);
    }
}

