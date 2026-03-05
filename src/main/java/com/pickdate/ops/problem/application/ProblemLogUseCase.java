package com.pickdate.ops.problem.application;

import com.pickdate.ops.problem.domain.ProblemLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProblemLogUseCase {

    void save(ProblemLog entity);

    Page<ProblemLog> getProblems(Pageable pageable);
}
