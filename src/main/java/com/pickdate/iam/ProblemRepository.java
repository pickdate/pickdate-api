package com.pickdate.iam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
interface ProblemRepository extends JpaRepository<ProblemEntity, String> {
}
