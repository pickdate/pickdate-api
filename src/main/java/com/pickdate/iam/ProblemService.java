package com.pickdate.iam;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
class ProblemService {

    private final ProblemRepository repository;

    void save(ProblemEntity entity) {
        repository.save(entity);
    }

    public Page<ProblemEntity> getProblems(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
