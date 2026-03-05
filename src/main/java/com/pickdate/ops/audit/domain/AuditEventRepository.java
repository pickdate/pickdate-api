package com.pickdate.ops.audit.domain;

import com.pickdate.bootstrap.domain.Identifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AuditEventRepository {

    Page<AuditLogEventEntity> findByAction(Action action, Pageable pageable);

    Page<AuditLogEventEntity> findByUserId(String userId, Pageable pageable);

    Page<AuditLogEventEntity> findAll(Pageable pageable);

    AuditLogEventEntity save(AuditLogEventEntity eventEntity);

    void deleteById(Identifier identifier);

    void deleteAll();

    Optional<AuditLogEventEntity> findById(Identifier identifier);
}
