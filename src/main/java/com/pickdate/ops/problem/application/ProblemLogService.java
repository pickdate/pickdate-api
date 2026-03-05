package com.pickdate.ops.problem.application;

import com.pickdate.ops.problem.domain.ProblemLog;
import com.pickdate.ops.problem.domain.ProblemLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
class ProblemLogService implements ProblemLogUseCase {

    private final ProblemLogRepository repository;

    @Override
    @Transactional
    public void save(ProblemLog entity) {
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProblemLog> getProblems(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
