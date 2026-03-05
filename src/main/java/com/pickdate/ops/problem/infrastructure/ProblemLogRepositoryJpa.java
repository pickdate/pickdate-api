package com.pickdate.ops.problem.infrastructure;

import com.pickdate.bootstrap.domain.Identifier;
import com.pickdate.ops.problem.domain.ProblemLog;
import com.pickdate.ops.problem.domain.ProblemLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
interface ProblemLogRepositoryJpa extends ProblemLogRepository, JpaRepository<ProblemLog, Identifier> {
}
