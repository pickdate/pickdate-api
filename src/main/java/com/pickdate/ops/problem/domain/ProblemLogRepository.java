package com.pickdate.ops.problem.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProblemLogRepository {

    ProblemLog save(ProblemLog entity);

    Page<ProblemLog> findAll(Pageable pageable);
}
