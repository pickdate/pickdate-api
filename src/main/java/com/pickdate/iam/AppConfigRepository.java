package com.pickdate.iam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
interface AppConfigRepository extends JpaRepository<AppConfigEntity, String> {

    @Query("FROM AppConfigEntity s WHERE s.id = 'app_config'")
    Optional<AppConfigEntity> findSetup();
}
